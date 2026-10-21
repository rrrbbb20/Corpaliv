
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColaboracionesScreen() {

    // 1. Datos del formulario
    var nombreEmpresa by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var tipoColaboracion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // 2. Estado del menú desplegable
    var menuExpandido by remember { mutableStateOf(false) }

    // 3. Errores de validación
    var errorEmpresa by remember { mutableStateOf("") }
    var errorTelefono by remember { mutableStateOf("") }
    var errorTipo by remember { mutableStateOf("") }
    var errorDescripcion by remember { mutableStateOf("") }

    // 4. Resultado del formulario
    var formularioValido by remember { mutableStateOf(false) }

    // 5. Tipos de colaboración disponibles
    val tipos = listOf(
        "Donación de recursos",
        "Apoyo a talleres",
        "Oportunidades laborales",
        "Otra colaboración"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Colaboración empresarial",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Envía una propuesta de colaboración " +
                    "en nombre de una empresa. " +
                    "Este formulario es una simulación académica."
        )

        Spacer(modifier = Modifier.height(24.dp))

        // NOMBRE DE LA EMPRESA
        OutlinedTextField(
            value = nombreEmpresa,
            onValueChange = {
                nombreEmpresa = it
                errorEmpresa = ""
                formularioValido = false
            },
            label = { Text("Nombre de la empresa") },
            isError = errorEmpresa.isNotEmpty(),
            supportingText = {
                if (errorEmpresa.isNotEmpty()) {
                    Text(errorEmpresa)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // TELÉFONO
        OutlinedTextField(
            value = telefono,
            onValueChange = { valor ->
                telefono = valor
                    .filter { it.isDigit() }
                    .take(9)

                errorTelefono = ""
                formularioValido = false
            },
            label = { Text("Teléfono de contacto") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            isError = errorTelefono.isNotEmpty(),
            supportingText = {
                if (errorTelefono.isNotEmpty()) {
                    Text(errorTelefono)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // TIPO DE COLABORACIÓN
        ExposedDropdownMenuBox(
            expanded = menuExpandido,
            onExpandedChange = {
                menuExpandido = !menuExpandido
            }
        ) {

            OutlinedTextField(
                value = tipoColaboracion,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de colaboración") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = menuExpandido
                    )
                },
                isError = errorTipo.isNotEmpty(),
                supportingText = {
                    if (errorTipo.isNotEmpty()) {
                        Text(errorTipo)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = menuExpandido,
                onDismissRequest = {
                    menuExpandido = false
                }
            ) {

                tipos.forEach { opcion ->

                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            tipoColaboracion = opcion
                            menuExpandido = false
                            errorTipo = ""
                            formularioValido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // DESCRIPCIÓN DE LA PROPUESTA
        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
                errorDescripcion = ""
                formularioValido = false
            },
            label = { Text("Describe tu propuesta") },
            minLines = 3,
            maxLines = 5,
            isError = errorDescripcion.isNotEmpty(),
            supportingText = {
                if (errorDescripcion.isNotEmpty()) {
                    Text(errorDescripcion)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // VALIDAR SOLICITUD
        Button(
            onClick = {

                errorEmpresa = if (nombreEmpresa.isBlank()) {
                    "Ingresa el nombre de la empresa."
                } else {
                    ""
                }

                errorTelefono = if (telefono.length != 9) {
                    "El teléfono debe tener 9 dígitos."
                } else {
                    ""
                }

                errorTipo = if (tipoColaboracion.isBlank()) {
                    "Selecciona un tipo de colaboración."
                } else {
                    ""
                }

                errorDescripcion =
                    if (descripcion.trim().length < 10) {
                        "La descripción debe tener al menos 10 caracteres."
                    } else {
                        ""
                    }

                formularioValido = listOf(
                    errorEmpresa,
                    errorTelefono,
                    errorTipo,
                    errorDescripcion
                ).all { it.isEmpty() }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar solicitud de prueba")
        }

        // RESULTADO
        if (formularioValido) {

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Solicitud validada",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Empresa: $nombreEmpresa")

                    Text("Teléfono: $telefono")

                    Text("Colaboración: $tipoColaboracion")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "La solicitud es de prueba. " +
                                "Todavía no se ha guardado ni enviado."
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Simulación académica. Utiliza datos ficticios.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
