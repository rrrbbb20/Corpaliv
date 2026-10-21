package com.example.corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.local.model.Solicitud
import com.example.corpaliv.data.repository.SolicitudRepository
import com.example.corpaliv.utils.Validaciones
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SolicitudUiState(
    val tipo: String = "Consulta general", val asunto: String = "", val descripcion: String = "",
    val aceptaCondiciones: Boolean = false,
    val errorAsunto: String = "", val errorDescripcion: String = "", val errorCondiciones: String = "",
    val guardando: Boolean = false, val mensaje: String = "",
    val solicitudes: List<Solicitud> = emptyList()
)

class SolicitudViewModel(private val repository: SolicitudRepository, private val usuarioId: Int) : ViewModel() {
    private val _uiState = MutableStateFlow(SolicitudUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observar(usuarioId).collect { datos ->
                _uiState.update { it.copy(solicitudes=datos) }
            }
        }
    }

    fun cambiarTipo(v: String) = _uiState.update { it.copy(tipo=v, mensaje="") }
    fun cambiarAsunto(v: String) = _uiState.update { it.copy(asunto=v, errorAsunto="", mensaje="") }
    fun cambiarDescripcion(v: String) = _uiState.update { it.copy(descripcion=v, errorDescripcion="", mensaje="") }
    fun cambiarCondiciones(v: Boolean) = _uiState.update { it.copy(aceptaCondiciones=v, errorCondiciones="", mensaje="") }

    fun guardar() {
        val d = _uiState.value
        if (d.guardando) return
        val ea = Validaciones.asunto(d.asunto)
        val ed = Validaciones.descripcion(d.descripcion)
        val ec = if (!d.aceptaCondiciones) "Debes aceptar las condiciones." else ""
        _uiState.update { it.copy(errorAsunto=ea, errorDescripcion=ed, errorCondiciones=ec, mensaje="") }
        if (listOf(ea,ed,ec).any { it.isNotEmpty() }) return
        _uiState.update { it.copy(guardando=true) }
        viewModelScope.launch {
            try {
                repository.crear(Solicitud(usuarioId=usuarioId, tipo=d.tipo,
                    asunto=d.asunto.trim(), descripcion=d.descripcion.trim()))
                _uiState.update { it.copy(asunto="", descripcion="", aceptaCondiciones=false,
                    guardando=false, mensaje="Solicitud guardada localmente (simulación).") }
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) { _uiState.update { it.copy(guardando=false, mensaje="No se pudo guardar.") } }
        }
    }

    fun avanzarEstado(solicitud: Solicitud) {
        val siguiente = when (solicitud.estado) {
            "Pendiente" -> "En revisión"
            "En revisión" -> "Respondida"
            else -> return
        }
        viewModelScope.launch {
            try { repository.actualizarEstado(solicitud.id, usuarioId, siguiente) }
            catch (e: CancellationException) { throw e }
            catch (_: Exception) { _uiState.update { it.copy(mensaje="No se pudo actualizar el estado.") } }
        }
    }
}
