
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgramasScreen(
    onIrEscuela: () -> Unit,
    onIrAlbaLab: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Nuestros programas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Conoce las iniciativas de CORPALIV " +
                    "para promover la inclusión y participación.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ESCUELA JAN VAN DIJK
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Escuela Especial Jan Van Dijk",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Programa educativo especializado " +
                            "para estudiantes con discapacidad múltiple."
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onIrEscuela,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Conocer la escuela")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ALBA LAB
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Alba Lab",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Programa sociolaboral que desarrolla " +
                            "formación y oportunidades de inclusión laboral."
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onIrAlbaLab,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Conocer Alba Lab")
                }
            }
        }
    }
}
