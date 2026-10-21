package com.example.corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

data class LoginUiState(
    val correo: String = "", val contrasena: String = "",
    val errorCorreo: String = "", val errorContrasena: String = "",
    val iniciandoSesion: Boolean = false, val usuarioId: Int? = null,
    val mensaje: String = ""
)

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun cambiarCorreo(v: String) = _uiState.update { it.copy(correo=v, errorCorreo="", errorContrasena="", mensaje="") }
    fun cambiarContrasena(v: String) = _uiState.update { it.copy(contrasena=v, errorContrasena="", mensaje="") }

    fun iniciarSesion() {
        val d = _uiState.value
        if (d.iniciandoSesion) return
        val correo = d.correo.trim().lowercase()
        val eCorreo = Validaciones.correo(correo)
        val eClave = if (d.contrasena.isBlank()) "Ingresa tu contraseña." else ""
        _uiState.update { it.copy(errorCorreo=eCorreo, errorContrasena=eClave, mensaje="") }
        if (eCorreo.isNotEmpty() || eClave.isNotEmpty()) return
        _uiState.update { it.copy(iniciandoSesion=true) }
        viewModelScope.launch {
            try {
                val usuario = repository.buscarPorCorreo(correo)
                val correcto = usuario != null && withContext(Dispatchers.Default) {
                    Contrasenas.verificar(d.contrasena, usuario.contrasenaHash)
                }
                if (correcto && usuario != null) {
                    _uiState.value = LoginUiState(usuarioId=usuario.id)
                } else {
                    _uiState.update { it.copy(iniciandoSesion=false, errorContrasena="Correo o contraseña incorrectos.") }
                }
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) { _uiState.update { it.copy(iniciandoSesion=false, mensaje="No se pudo iniciar sesión.") } }
        }
    }
}
