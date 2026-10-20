
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DonacionesScreen(
    onIrSimularDonacion: () -> Unit,
    onIrColaboracionEmpresa: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Colabora con CORPALIV",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Tu apoyo puede contribuir al desarrollo " +
                    "de los programas de CORPALIV.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // DONACIONES DE PERSONAS
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Donaciones de personas",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Realiza una donación de prueba " +
                            "seleccionando un monto para apoyar " +
                            "los programas de CORPALIV."
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onIrSimularDonacion,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Simular donación")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // COLABORACIÓN EMPRESARIAL
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Colaboración empresarial",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Las empresas pueden conocer " +
                            "alternativas de colaboración " +
                            "y registrar solicitudes de prueba."
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onIrColaboracionEmpresa,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Solicitar colaboración")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


    }
}
