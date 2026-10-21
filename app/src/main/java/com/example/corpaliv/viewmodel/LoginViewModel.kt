
package com.example.corpaliv.viewmodel

import android.util.Base64
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.local.model.Usuario
import com.example.corpaliv.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",
    val errorCorreo: String = "",
    val errorContrasena: String = "",
    val iniciandoSesion: Boolean = false,
    val usuarioAutenticado: Usuario? = null,
    val mensaje: String = ""
)

class LoginViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun cambiarCorreo(valor: String) {
        _uiState.update {
            it.copy(
                correo = valor,
                errorCorreo = "",
                mensaje = ""
            )
        }
    }

    fun cambiarContrasena(valor: String) {
        _uiState.update {
            it.copy(
                contrasena = valor,
                errorContrasena = "",
                mensaje = ""
            )
        }
    }

    fun iniciarSesion() {
        val datos = _uiState.value

        if (datos.iniciandoSesion) return

        val correo = datos.correo.trim().lowercase()

        val errorCorreo = if (
            !Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        ) {
            "Ingresa un correo válido."
        } else {
            ""
        }

        val errorContrasena = if (datos.contrasena.isBlank()) {
            "Ingresa tu contraseña."
        } else {
            ""
        }

        _uiState.update {
            it.copy(
                errorCorreo = errorCorreo,
                errorContrasena = errorContrasena,
                mensaje = ""
            )
        }

        if (errorCorreo.isNotEmpty() || errorContrasena.isNotEmpty()) {
            return
        }

        _uiState.update {
            it.copy(iniciandoSesion = true)
        }

        viewModelScope.launch {
            try {
                val usuario = repository.buscarPorCorreo(correo)

                val contrasenaCorrecta = usuario != null &&
                        verificarContrasena(
                            datos.contrasena,
                            usuario.contrasenaHash
                        )

                if (contrasenaCorrecta && usuario != null) {
                    _uiState.value = LoginUiState(
                        usuarioAutenticado = usuario,
                        mensaje = "Inicio de sesión correcto."
                    )
                } else {
                    _uiState.update {
                        it.copy(
                            iniciandoSesion = false,
                            errorContrasena = "Correo o contraseña incorrectos."
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        iniciandoSesion = false,
                        mensaje = "No se pudo iniciar sesión."
                    )
                }
            }
        }
    }

    private fun verificarContrasena(
        contrasena: String,
        hashGuardado: String
    ): Boolean {

        val partes = hashGuardado.split(":")

        if (partes.size != 4) return false

        val algoritmo = partes[0]
        val iteraciones = partes[1].toIntOrNull() ?: return false

        if (
            algoritmo != "PBKDF2WithHmacSHA256" &&
            algoritmo != "PBKDF2WithHmacSHA1"
        ) {
            return false
        }

        if (iteraciones !in 1..1_000_000) return false

        val salt = Base64.decode(partes[2], Base64.NO_WRAP)
        val hashOriginal = Base64.decode(partes[3], Base64.NO_WRAP)

        val especificacion = PBEKeySpec(
            contrasena.toCharArray(),
            salt,
            iteraciones,
            hashOriginal.size * 8
        )

        return try {
            val hashCalculado = SecretKeyFactory
                .getInstance(algoritmo)
                .generateSecret(especificacion)
                .encoded

            MessageDigest.isEqual(
                hashOriginal,
                hashCalculado
            )
        } finally {
            especificacion.clearPassword()
        }
    }
}
