package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.corpaliv.ui.components.CampoTexto
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.MensajeAnimado
import com.example.corpaliv.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginExitoso: (Int) -> Unit, onIrRegistro: () -> Unit) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(estado.usuarioId) {
        estado.usuarioId?.let { onLoginExitoso(it) }
    }
    ColumnaPantalla("Iniciar sesión", "Ingresa con tu cuenta local de demostración.") {
        CampoTexto(estado.correo, viewModel::cambiarCorreo, "Correo electrónico", estado.errorCorreo,
            KeyboardType.Email, habilitado=!estado.iniciandoSesion)
        CampoTexto(estado.contrasena, viewModel::cambiarContrasena, "Contraseña", estado.errorContrasena,
            KeyboardType.Password, contrasena=true, habilitado=!estado.iniciandoSesion)
        Button(onClick=viewModel::iniciarSesion, enabled=!estado.iniciandoSesion,
            modifier=Modifier.fillMaxWidth()) { Text(if (estado.iniciandoSesion) "Ingresando..." else "Ingresar") }
        MensajeAnimado(estado.mensaje)
        TextButton(onClick=onIrRegistro, modifier=Modifier.fillMaxWidth()) { Text("¿No tienes cuenta? Regístrate") }
    }
}
