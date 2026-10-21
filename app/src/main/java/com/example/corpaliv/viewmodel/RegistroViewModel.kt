
package com.example.corpaliv.viewmodel

import android.os.Build
import android.util.Base64
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.local.model.Usuario
import com.example.corpaliv.data.repository.UsuarioRepository
import com.example.corpaliv.utils.validarRut
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

data class RegistroUiState(
    val nombre: String = "",
    val rut: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val aceptaTerminos: Boolean = false,

    val errorNombre: String = "",
    val errorRut: String = "",
    val errorCorreo: String = "",
    val errorContrasena: String = "",
    val errorConfirmacion: String = "",
    val errorTerminos: String = "",

    val registrando: Boolean = false,
    val mensaje: String = ""
)

class RegistroViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState = _uiState.asStateFlow()

    // Actualizar los campos del formulario

    fun cambiarNombre(valor: String) {
        _uiState.update {
            it.copy(
                nombre = valor,
                errorNombre = "",
                mensaje = ""
            )
        }
    }

    fun cambiarRut(valor: String) {
        _uiState.update {
            it.copy(
                rut = valor,
                errorRut = "",
                mensaje = ""
            )
        }
    }

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

    fun cambiarConfirmacion(valor: String) {
        _uiState.update {
            it.copy(
                confirmarContrasena = valor,
                errorConfirmacion = "",
                mensaje = ""
            )
        }
    }

    fun cambiarTerminos(valor: Boolean) {
        _uiState.update {
            it.copy(
                aceptaTerminos = valor,
                errorTerminos = "",
                mensaje = ""
            )
        }
    }

    // Validar y registrar usuario

    fun registrar() {
        val datos = _uiState.value

        if (datos.registrando) return

        val nombre = datos.nombre.trim()

        val rut = datos.rut.trim()
            .replace(".", "")
            .uppercase()

        val correo = datos.correo.trim().lowercase()

        val errorNombre = if (nombre.length < 2) {
            "Ingresa un nombre válido."
        } else {
            ""
        }

        val errorRut = if (!validarRut(rut)) {
            "Ingresa un RUT válido."
        } else {
            ""
        }

        val errorCorreo = if (
            !Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        ) {
            "Ingresa un correo válido."
        } else {
            ""
        }

        val errorContrasena = if (datos.contrasena.length < 8) {
            "La contraseña debe tener al menos 8 caracteres."
        } else {
            ""
        }

        val errorConfirmacion = if (
            datos.confirmarContrasena != datos.contrasena
        ) {
            "Las contraseñas no coinciden."
        } else {
            ""
        }

        val errorTerminos = if (!datos.aceptaTerminos) {
            "Debes aceptar los términos."
        } else {
            ""
        }

        _uiState.update {
            it.copy(
                errorNombre = errorNombre,
                errorRut = errorRut,
                errorCorreo = errorCorreo,
                errorContrasena = errorContrasena,
                errorConfirmacion = errorConfirmacion,
                errorTerminos = errorTerminos,
                mensaje = ""
            )
        }

        val hayErrores = listOf(
            errorNombre,
            errorRut,
            errorCorreo,
            errorContrasena,
            errorConfirmacion,
            errorTerminos
        ).any { it.isNotEmpty() }

        if (hayErrores) return

        _uiState.update {
            it.copy(registrando = true)
        }

        viewModelScope.launch {
            try {

                // Comprobar si existe el RUT

                if (repository.buscarPorRut(rut) != null) {
                    _uiState.update {
                        it.copy(
                            errorRut = "Este RUT ya está registrado.",
                            registrando = false
                        )
                    }
                    return@launch
                }

                // Comprobar si existe el correo

                if (repository.buscarPorCorreo(correo) != null) {
                    _uiState.update {
                        it.copy(
                            errorCorreo = "Este correo ya está registrado.",
                            registrando = false
                        )
                    }
                    return@launch
                }

                // Generar hash de contraseña

                val hash = generarHash(datos.contrasena)

                // Crear usuario

                val usuario = Usuario(
                    nombre = nombre,
                    rut = rut,
                    correo = correo,
                    contrasenaHash = hash
                )

                // Guardar en Room

                repository.registrarUsuario(usuario)

                // Limpiar formulario

                _uiState.value = RegistroUiState(
                    mensaje = "Usuario registrado correctamente."
                )

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        registrando = false,
                        mensaje = "No se pudo registrar el usuario."
                    )
                }
            }
        }
    }

    // Generar hash seguro de la contraseña

    private fun generarHash(contrasena: String): String {

        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)

        val algoritmo = if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        ) {
            "PBKDF2WithHmacSHA256"
        } else {
            "PBKDF2WithHmacSHA1"
        }

        val iteraciones = 120000

        val especificacion = PBEKeySpec(
            contrasena.toCharArray(),
            salt,
            iteraciones,
            256
        )

        return try {

            val clave = SecretKeyFactory
                .getInstance(algoritmo)
                .generateSecret(especificacion)
                .encoded

            val saltBase64 = Base64.encodeToString(
                salt,
                Base64.NO_WRAP
            )

            val hashBase64 = Base64.encodeToString(
                clave,
                Base64.NO_WRAP
            )

            "$algoritmo:$iteraciones:$saltBase64:$hashBase64"

        } finally {
            especificacion.clearPassword()
        }
    }
}
