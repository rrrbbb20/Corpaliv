package com.example.corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.local.model.Usuario
import com.example.corpaliv.data.repository.UsuarioRepository
import com.example.corpaliv.utils.Contrasenas
import com.example.corpaliv.utils.Validaciones
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// La vista solo dibuja estos estados; las reglas viven fuera del Composable.
data class RegistroUiState(
    val nombre: String = "", val rut: String = "", val correo: String = "",
    val contrasena: String = "", val confirmarContrasena: String = "",
    val aceptaTerminos: Boolean = false,
    val errorNombre: String = "", val errorRut: String = "", val errorCorreo: String = "",
    val errorContrasena: String = "", val errorConfirmacion: String = "", val errorTerminos: String = "",
    val registrando: Boolean = false, val mensaje: String = ""
)

class RegistroViewModel(private val repository: UsuarioRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState = _uiState.asStateFlow()

    fun cambiarNombre(v: String) = _uiState.update { it.copy(nombre=v, errorNombre="", mensaje="") }
    fun cambiarRut(v: String) = _uiState.update { it.copy(rut=v, errorRut="", mensaje="") }
    fun cambiarCorreo(v: String) = _uiState.update { it.copy(correo=v, errorCorreo="", mensaje="") }
    fun cambiarContrasena(v: String) = _uiState.update { it.copy(contrasena=v, errorContrasena="", mensaje="") }
    fun cambiarConfirmacion(v: String) = _uiState.update { it.copy(confirmarContrasena=v, errorConfirmacion="", mensaje="") }
    fun cambiarTerminos(v: Boolean) = _uiState.update { it.copy(aceptaTerminos=v, errorTerminos="", mensaje="") }

    fun registrar() {
        val d = _uiState.value
        if (d.registrando) return
        val nombre = d.nombre.trim()
        val rut = d.rut.trim().replace(".", "").replace(" ", "").uppercase()
        val correo = d.correo.trim().lowercase()
        val eNombre = Validaciones.nombre(nombre)
        val eRut = Validaciones.rut(rut)
        val eCorreo = Validaciones.correo(correo)
        val eClave = Validaciones.contrasena(d.contrasena)
        val eConfirmar = if (d.confirmarContrasena != d.contrasena) "Las contraseñas no coinciden." else ""
        val eTerminos = if (!d.aceptaTerminos) "Debes aceptar los términos." else ""
        _uiState.update { it.copy(errorNombre=eNombre, errorRut=eRut, errorCorreo=eCorreo,
            errorContrasena=eClave, errorConfirmacion=eConfirmar, errorTerminos=eTerminos, mensaje="") }
        if (listOf(eNombre,eRut,eCorreo,eClave,eConfirmar,eTerminos).any { it.isNotEmpty() }) return
        _uiState.update { it.copy(registrando=true) }
        viewModelScope.launch {
            try {
                if (repository.buscarPorRut(rut) != null) {
                    _uiState.update { it.copy(errorRut="Este RUT ya está registrado.", registrando=false) }
                    return@launch
                }
                if (repository.buscarPorCorreo(correo) != null) {
                    _uiState.update { it.copy(errorCorreo="Este correo ya está registrado.", registrando=false) }
                    return@launch
                }
                val hash = withContext(Dispatchers.Default) { Contrasenas.crearHash(d.contrasena) }
                repository.registrarUsuario(Usuario(nombre=nombre, rut=rut, correo=correo, contrasenaHash=hash))
                _uiState.value = RegistroUiState(mensaje="Usuario registrado correctamente. Ahora puedes iniciar sesión.")
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) {
                _uiState.update { it.copy(registrando=false, mensaje="No se pudo registrar. Revisa si el correo o RUT ya existe.") }
            }
        }
    }
}
