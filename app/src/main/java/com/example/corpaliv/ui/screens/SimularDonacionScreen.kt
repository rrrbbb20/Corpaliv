
package com.example.corpaliv.ui.screens

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

@Composable
fun SimularDonacionScreen() {

    // 1. Estados del monto
    var montoSeleccionado by remember {
        mutableStateOf("5000")
    }
    var otroMonto by remember { mutableStateOf("") }

    // 2. Datos ficticios del donante
    var nombre by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var tarjeta by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }

    // 3. Errores y confirmación
    var errorMonto by remember { mutableStateOf("") }
    var errorNombre by remember { mutableStateOf("") }
    var errorCiudad by remember { mutableStateOf("") }
    var errorDireccion by remember { mutableStateOf("") }
    var errorTarjeta by remember { mutableStateOf("") }
    var errorCvc by remember { mutableStateOf("") }
    var confirmada by remember { mutableStateOf(false) }

    val montoFinal = if (montoSeleccionado == "otro") {
        otroMonto.toLongOrNull()
    } else {
        montoSeleccionado.toLongOrNull()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Donación simulada",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Selecciona un monto y utiliza únicamente " +
                    "datos ficticios. No se realizará ningún cobro.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Monto de la donación",
            style = MaterialTheme.typography.titleLarge
        )

        val montos = listOf(
            "3000" to "$3.000",
            "5000" to "$5.000",
            "10000" to "$10.000",
            "otro" to "Otro monto"
        )

        montos.forEach { (valor, etiqueta) ->

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = montoSeleccionado == valor,
                    onClick = {
                        montoSeleccionado = valor
                        errorMonto = ""
                        confirmada = false
                    }
                )

                Text(etiqueta)
            }
        }

        if (montoSeleccionado == "otro") {

            OutlinedTextField(
                value = otroMonto,
                onValueChange = { valor ->
                    otroMonto = valor
                        .filter { it.isDigit() }
                        .take(9)
                    errorMonto = ""
                    confirmada = false
                },
                label = { Text("Otro monto (CLP)") },
                placeholder = { Text("Ejemplo: 15000") },
                prefix = { Text("$") },
                isError = errorMonto.isNotEmpty(),
                supportingText = {
                    if (errorMonto.isNotEmpty()) {
                        Text(errorMonto)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        } else if (errorMonto.isNotEmpty()) {
            Text(
                text = errorMonto,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Datos del donante",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                errorNombre = ""
                confirmada = false
            },
            label = { Text("Nombre del donante") },
            isError = errorNombre.isNotEmpty(),
            supportingText = {
                if (errorNombre.isNotEmpty()) Text(errorNombre)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = ciudad,
            onValueChange = {
                ciudad = it
                errorCiudad = ""
                confirmada = false
            },
            label = { Text("Ciudad") },
            isError = errorCiudad.isNotEmpty(),
            supportingText = {
                if (errorCiudad.isNotEmpty()) Text(errorCiudad)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = direccion,
            onValueChange = {
                direccion = it
                errorDireccion = ""
                confirmada = false
            },
            label = { Text("Dirección ficticia") },
            isError = errorDireccion.isNotEmpty(),
            supportingText = {
                if (errorDireccion.isNotEmpty()) Text(errorDireccion)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Pago de demostración",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Los siguientes códigos son ficticios. " +
                    "No ingreses datos de tarjetas reales.",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = tarjeta,
            onValueChange = { valor ->
                tarjeta = valor
                    .filter { it.isDigit() }
                    .take(12)
                errorTarjeta = ""
                confirmada = false
            },
            label = { Text("Código de tarjeta ficticia") },
            placeholder = { Text("000000000000") },
            isError = errorTarjeta.isNotEmpty(),
            supportingText = {
                if (errorTarjeta.isNotEmpty()) {
                    Text(errorTarjeta)
                } else {
                    Text("Exactamente 12 dígitos de prueba")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = cvc,
            onValueChange = { valor ->
                cvc = valor
                    .filter { it.isDigit() }
                    .take(3)
                errorCvc = ""
                confirmada = false
            },
            label = { Text("Código de seguridad ficticio") },
            placeholder = { Text("000") },
            isError = errorCvc.isNotEmpty(),
            supportingText = {
                if (errorCvc.isNotEmpty()) {
                    Text(errorCvc)
                } else {
                    Text("Exactamente 3 dígitos de prueba")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {

                errorMonto = ""
                errorNombre = ""
                errorCiudad = ""
                errorDireccion = ""
                errorTarjeta = ""
                errorCvc = ""

                if (montoFinal == null || montoFinal <= 0L) {
                    errorMonto = "Ingresa un monto mayor que cero."
                }

                if (nombre.isBlank()) {
                    errorNombre = "Debes ingresar un nombre ficticio."
                }

                if (ciudad.isBlank()) {
                    errorCiudad = "Debes ingresar la ciudad."
                }

                if (direccion.isBlank()) {
                    errorDireccion = "Debes ingresar una dirección ficticia."
                }

                if (tarjeta.length != 12) {
                    errorTarjeta = "El código debe tener 12 dígitos."
                }

                if (cvc.length != 3) {
                    errorCvc = "El código debe tener 3 dígitos."
                }

                confirmada =
                    errorMonto.isEmpty() &&
                            errorNombre.isEmpty() &&
                            errorCiudad.isEmpty() &&
                            errorDireccion.isEmpty() &&
                            errorTarjeta.isEmpty() &&
                            errorCvc.isEmpty()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar donación simulada")
        }

        if (confirmada) {

            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Donación de prueba confirmada",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(8.dp))

                    Text("Monto: $${montoFinal ?: 0L} CLP")
                    Text("Donante ficticio: $nombre")

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "No se realizó ningún cobro " +
                                "ni se almacenaron datos bancarios."
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Simulación académica. No corresponde " +
                    "a una donación real a CORPALIV.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
