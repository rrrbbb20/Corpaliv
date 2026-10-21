package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.corpaliv.ui.components.CampoTexto
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.MensajeAnimado
import com.example.corpaliv.viewmodel.RegistroViewModel

@Composable
fun RegistroPersonaScreen(viewModel: RegistroViewModel, onIrLogin: () -> Unit) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()
    ColumnaPantalla("Registro de usuario", "Para esta evaluación, utiliza exclusivamente datos ficticios.") {
        CampoTexto(estado.nombre, viewModel::cambiarNombre, "Nombre completo", estado.errorNombre, habilitado=!estado.registrando)
        CampoTexto(estado.rut, viewModel::cambiarRut, "RUT ficticio (ej. 12345678-5)", estado.errorRut, habilitado=!estado.registrando)
        CampoTexto(estado.correo, viewModel::cambiarCorreo, "Correo electrónico", estado.errorCorreo, KeyboardType.Email, habilitado=!estado.registrando)
        CampoTexto(estado.contrasena, viewModel::cambiarContrasena, "Contraseña", estado.errorContrasena, KeyboardType.Password, contrasena=true, habilitado=!estado.registrando)
        CampoTexto(estado.confirmarContrasena, viewModel::cambiarConfirmacion, "Confirmar contraseña", estado.errorConfirmacion, KeyboardType.Password, contrasena=true, habilitado=!estado.registrando)
        Row(modifier=Modifier.fillMaxWidth()) {
            Checkbox(checked=estado.aceptaTerminos, onCheckedChange=viewModel::cambiarTerminos, enabled=!estado.registrando)
            Text("Acepto las condiciones de esta demostración", modifier=Modifier.padding(top=12.dp))
        }
        if (estado.errorTerminos.isNotBlank()) Text(estado.errorTerminos, color=MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(16.dp))
        Button(onClick=viewModel::registrar, enabled=!estado.registrando, modifier=Modifier.fillMaxWidth()) {
            Text(if (estado.registrando) "Registrando..." else "Registrarse")
        }
        MensajeAnimado(estado.mensaje)
        TextButton(onClick=onIrLogin, modifier=Modifier.fillMaxWidth()) { Text("Ya tengo cuenta / Iniciar sesión") }
    }
}
