package com.example.corpaliv.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.repository.UsuarioRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SesionUiState(
    val cargando: Boolean = true,
    val usuarioId: Int? = null,
    val nombre: String = "", val correo: String = "", val rut: String = ""
)

// Solo se guarda el ID en preferencias. Contraseña y hash NO se guardan en la sesión.
// Autenticación local de demostración: no apta para un backend real.
class SesionViewModel(context: Context, private val repository: UsuarioRepository) : ViewModel() {
    private val preferencias = context.applicationContext.getSharedPreferences("sesion_corpaliv", Context.MODE_PRIVATE)
    private val _uiState = MutableStateFlow(SesionUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val idGuardado = preferencias.getInt("usuario_id", -1)
        if (idGuardado > 0) cargarUsuario(idGuardado)
        else _uiState.value = SesionUiState(cargando=false)
    }

    fun iniciar(id: Int) {
        preferencias.edit().putInt("usuario_id", id).apply()
        cargarUsuario(id)
    }

    private fun cargarUsuario(id: Int) {
        _uiState.value = SesionUiState(cargando=true)
        viewModelScope.launch {
            try {
                val usuario = repository.buscarPorId(id)
                if (usuario != null) _uiState.value = SesionUiState(
                    cargando=false, usuarioId=usuario.id,
                    nombre=usuario.nombre, correo=usuario.correo, rut=usuario.rut
                ) else cerrarSesion()
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) { _uiState.value = SesionUiState(cargando=false) }
        }
    }

    fun cerrarSesion() {
        preferencias.edit().remove("usuario_id").apply()
        _uiState.value = SesionUiState(cargando=false)
    }
}
