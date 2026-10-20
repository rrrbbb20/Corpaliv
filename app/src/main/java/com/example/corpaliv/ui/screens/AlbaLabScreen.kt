
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlbaLabScreen(
    onIrTalleres: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Alba Lab",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Programa sociolaboral de CORPALIV.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "¿Qué es Alba Lab?",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Alba Lab promueve el desarrollo " +
                            "de habilidades sociolaborales " +
                            "y oportunidades de participación " +
                            "e inclusión laboral."
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Formación y talleres",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Explora talleres de demostración " +
                            "orientados al aprendizaje de " +
                            "habilidades para la vida y el trabajo."
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onIrTalleres,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver talleres disponibles")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Inclusión sociolaboral",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "La formación y colaboración " +
                            "con distintos actores permite " +
                            "impulsar oportunidades de " +
                            "participación sociolaboral."
                )
            }
        }
    }
}
