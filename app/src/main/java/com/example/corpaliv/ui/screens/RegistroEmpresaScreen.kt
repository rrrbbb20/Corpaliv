
package com.example.corpaliv.ui.screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.corpaliv.utils.validarRut

@Composable
fun RegistroEmpresaScreen() {

    // Estados del formulario
    var razonSocial by remember { mutableStateOf("") }
    var rutEmpresa by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    // Mensaje de validación
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Registro de empresa",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Registra tu empresa para colaborar con CORPALIV.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Razón social
        OutlinedTextField(
            value = razonSocial,
            onValueChange = { razonSocial = it },
            label = { Text("Razón social") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // RUT de empresa
        OutlinedTextField(
            value = rutEmpresa,
            onValueChange = { rutEmpresa = it },
            label = { Text("RUT de empresa") },
            placeholder = { Text("76.123.456-7") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre de contacto
        OutlinedTextField(
            value = contacto,
            onValueChange = { contacto = it },
            label = { Text("Nombre de contacto") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Correo electrónico
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Teléfono
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono de contacto") },
            placeholder = { Text("912345678") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contraseña
        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirmar contraseña
        OutlinedTextField(
            value = confirmarContrasena,
            onValueChange = { confirmarContrasena = it },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Términos y condiciones
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Checkbox(
                checked = aceptaTerminos,
                onCheckedChange = { aceptaTerminos = it }
            )

            Text("Acepto los términos y condiciones")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Validar formulario
        Button(
            onClick = {

                mensaje = when {

                    razonSocial.isBlank() ->
                        "Debes ingresar la razón social."

                    rutEmpresa.isBlank() ->
                        "Debes ingresar el RUT de empresa."

                    !validarRut(rutEmpresa) ->
                        "El RUT de empresa no es válido."

                    contacto.isBlank() ->
                        "Debes ingresar un nombre de contacto."

                    correo.isBlank() ->
                        "Debes ingresar el correo."

                    !Patterns.EMAIL_ADDRESS
                        .matcher(correo.trim()).matches() ->
                        "El correo electrónico no es válido."

                    !telefono.matches(Regex("^[0-9]{9}$")) ->
                        "El teléfono debe tener 9 dígitos."

                    contrasena.length < 8 ->
                        "La contraseña debe tener al menos 8 caracteres."

                    contrasena != confirmarContrasena ->
                        "Las contraseñas no coinciden."

                    !aceptaTerminos ->
                        "Debes aceptar los términos y condiciones."

                    else ->
                        "Formulario válido. Aún no se ha guardado la empresa."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar empresa")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
