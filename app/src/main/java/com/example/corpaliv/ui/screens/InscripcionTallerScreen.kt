
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
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InscripcionTallerScreen(
    nombreTaller: String
) {

    // 1. Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    var aceptaCondiciones by remember {
        mutableStateOf(false)
    }

    var recibirNovedades by remember {
        mutableStateOf(false)
    }

    var mensaje by remember {
        mutableStateOf("")
    }

    // 2. Estado del selector de fecha
    var mostrarCalendario by remember {
        mutableStateOf(false)
    }

    val fechaState = rememberDatePickerState()

    val fechaSeleccionada = fechaState.selectedDateMillis?.let {
        SimpleDateFormat(
            "dd/MM/yyyy",
            Locale("es", "CL")
        ).format(Date(it))
    } ?: ""

    // 3. Calendario
    if (mostrarCalendario) {

        DatePickerDialog(
            onDismissRequest = {
                mostrarCalendario = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarCalendario = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarCalendario = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = fechaState)
        }
    }

    // 4. Interfaz del formulario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Inscripción a taller",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Taller seleccionado:",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = nombreTaller,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del participante") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico de prueba") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = edad,
            onValueChange = { valor ->
                edad = valor.filter { it.isDigit() }
            },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                mostrarCalendario = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (fechaSeleccionada.isEmpty()) {
                    "Seleccionar fecha de inscripción"
                } else {
                    "Fecha: $fechaSeleccionada"
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Checkbox(
                checked = aceptaCondiciones,
                onCheckedChange = {
                    aceptaCondiciones = it
                }
            )

            Text("Acepto las condiciones de la simulación")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Recibir novedades",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = recibirNovedades,
                onCheckedChange = {
                    recibirNovedades = it
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                mensaje = when {

                    nombre.isBlank() ->
                        "Debes ingresar el nombre."

                    correo.isBlank() ->
                        "Debes ingresar el correo."

                    !Patterns.EMAIL_ADDRESS
                        .matcher(correo.trim()).matches() ->
                        "El correo no es válido."

                    edad.toIntOrNull() == null ->
                        "Debes ingresar una edad válida."

                    edad.toInt() !in 1..120 ->
                        "La edad debe estar entre 1 y 120."

                    fechaSeleccionada.isEmpty() ->
                        "Debes seleccionar una fecha."

                    !aceptaCondiciones ->
                        "Debes aceptar las condiciones."

                    else ->
                        "Formulario válido. " +
                                "La inscripción aún no se ha guardado."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar inscripción de prueba")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mensaje.isNotEmpty()) {

            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Esta es una inscripción académica " +
                    "de demostración. Utiliza datos ficticios.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
