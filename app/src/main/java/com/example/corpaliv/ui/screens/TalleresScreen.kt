
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TalleresScreen(
    onIrInscripcion: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Talleres de Alba Lab",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Conoce nuestros talleres de demostración " +
                    "y selecciona uno para realizar " +
                    "una inscripción de prueba.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        TarjetaTaller(
            nombre = "Habilidades sociolaborales",
            descripcion = "Actividades para desarrollar " +
                    "la comunicación, colaboración " +
                    "y trabajo en equipo.",
            onInscribir = {
                onIrInscripcion("Habilidades sociolaborales")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TarjetaTaller(
            nombre = "Habilidades digitales",
            descripcion = "Introducción al uso de " +
                    "herramientas digitales " +
                    "para actividades cotidianas.",
            onInscribir = {
                onIrInscripcion("Habilidades digitales")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TarjetaTaller(
            nombre = "Preparación laboral",
            descripcion = "Actividades prácticas " +
                    "relacionadas con hábitos, " +
                    "organización y habilidades laborales.",
            onInscribir = {
                onIrInscripcion("Preparación laboral")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Importante: los talleres mostrados " +
                    "son ejemplos académicos. " +
                    "Las inscripciones no corresponden " +
                    "a postulaciones reales.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun TarjetaTaller(
    nombre: String,
    descripcion: String,
    onInscribir: () -> Unit
) {

    Card(modifier = Modifier.fillMaxWidth()) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = nombre,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = descripcion)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onInscribir,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Inscribirme")
            }
        }
    }
}
