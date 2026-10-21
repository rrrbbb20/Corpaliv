
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.corpaliv.viewmodel.RegistroViewModel

@Composable
fun RegistroPersonaScreen(
    viewModel: RegistroViewModel
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Registro de usuario",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Nombre

        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::cambiarNombre,
            label = { Text("Nombre completo") },
            isError = estado.errorNombre.isNotEmpty(),
            supportingText = {
                if (estado.errorNombre.isNotEmpty()) {
                    Text(estado.errorNombre)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !estado.registrando
        )

        Spacer(modifier = Modifier.height(12.dp))

        // RUT

        OutlinedTextField(
            value = estado.rut,
            onValueChange = viewModel::cambiarRut,
            label = { Text("RUT") },
            placeholder = { Text("12345678-9") },
            isError = estado.errorRut.isNotEmpty(),
            supportingText = {
                if (estado.errorRut.isNotEmpty()) {
                    Text(estado.errorRut)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !estado.registrando
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Correo

        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::cambiarCorreo,
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = estado.errorCorreo.isNotEmpty(),
            supportingText = {
                if (estado.errorCorreo.isNotEmpty()) {
                    Text(estado.errorCorreo)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !estado.registrando
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Contraseña

        OutlinedTextField(
            value = estado.contrasena,
            onValueChange = viewModel::cambiarContrasena,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = estado.errorContrasena.isNotEmpty(),
            supportingText = {
                if (estado.errorContrasena.isNotEmpty()) {
                    Text(estado.errorContrasena)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !estado.registrando
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Confirmar contraseña

        OutlinedTextField(
            value = estado.confirmarContrasena,
            onValueChange = viewModel::cambiarConfirmacion,
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = estado.errorConfirmacion.isNotEmpty(),
            supportingText = {
                if (estado.errorConfirmacion.isNotEmpty()) {
                    Text(estado.errorConfirmacion)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !estado.registrando
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Aceptar términos

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = viewModel::cambiarTerminos,
                enabled = !estado.registrando
            )

            Text(
                text = "Acepto los términos y condiciones",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        if (estado.errorTerminos.isNotEmpty()) {
            Text(
                text = estado.errorTerminos,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Registrar

        Button(
            onClick = viewModel::registrar,
            enabled = !estado.registrando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (estado.registrando) {
                    "Registrando..."
                } else {
                    "Registrarse"
                }
            )
        }

        // Mensaje de resultado

        if (estado.mensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = estado.mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
