
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onIrRegistro: () -> Unit,
    onIrLogin: () -> Unit,
    onIrQuienesSomos: () -> Unit,
    onIrProgramas: () -> Unit,
    onIrDonaciones: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Transformamos vidas",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Apoyamos el desarrollo y la inclusión " +
                    "de personas con discapacidad múltiple.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onIrDonaciones,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiero colaborar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Descubre CORPALIV",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            onClick = onIrQuienesSomos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Quiénes somos",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Conoce nuestra historia y propósito."
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            onClick = onIrProgramas,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Nuestros programas",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Escuela Jan Van Dijk y Alba Lab."
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            onClick = onIrDonaciones,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Donaciones",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Descubre cómo apoyar nuestra labor."
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Mi cuenta",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onIrLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onIrRegistro,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta")
        }
    }
}
