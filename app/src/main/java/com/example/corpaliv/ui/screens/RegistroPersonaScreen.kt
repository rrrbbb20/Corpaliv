
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
fun RegistroPersonaScreen() {
    // 1. Estados de los campos del formulario
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    // 2. Estado para mostrar mensajes de validación
    var mensaje by remember { mutableStateOf("") }

    // 3. Contenedor principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Título
        Text(
            text = "Registro de persona natural",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Completa tus datos para crear una cuenta.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Nombre completo
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 5. RUT chileno
        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("RUT chileno") },
            placeholder = { Text("12.345.678-5") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 6. Correo electrónico
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

        // 7. Contraseña
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

        // 8. Confirmar contraseña
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

        // 9. Aceptación de términos
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Checkbox(
                checked = aceptaTerminos,
                onCheckedChange = { aceptaTerminos = it }
            )

            Text(
                text = "Acepto los términos y condiciones"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 10. Botón para validar el formulario
        Button(
            onClick = {

                mensaje = when {

                    nombre.isBlank() ->
                        "Debes ingresar tu nombre."

                    rut.isBlank() ->
                        "Debes ingresar tu RUT."

                    !validarRut(rut) ->
                        "El RUT ingresado no es válido."

                    correo.isBlank() ->
                        "Debes ingresar tu correo."

                    !Patterns.EMAIL_ADDRESS
                        .matcher(correo.trim()).matches() ->
                        "El correo electrónico no es válido."

                    contrasena.length < 8 ->
                        "La contraseña debe tener al menos 8 caracteres."

                    contrasena != confirmarContrasena ->
                        "Las contraseñas no coinciden."

                    !aceptaTerminos ->
                        "Debes aceptar los términos y condiciones."

                    else ->
                        "Formulario válido. Aún no se ha guardado la cuenta."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarme")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 11. Mostrar el resultado de la validación
        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
