package com.example.corpaliv.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun TalleresScreen(onIrInscripcion: (String) -> Unit) {
    ColumnaPantalla("Talleres de Alba Lab", "Selecciona un taller de demostración y completa una inscripción de prueba.") {
        TarjetaInfo("Habilidades sociolaborales", "Comunicación, colaboración y trabajo en equipo.", "Inscribirme") { onIrInscripcion("Habilidades sociolaborales") }
        TarjetaInfo("Habilidades digitales", "Introducción a herramientas digitales para actividades cotidianas.", "Inscribirme") { onIrInscripcion("Habilidades digitales") }
        TarjetaInfo("Preparación laboral", "Hábitos, organización y habilidades laborales.", "Inscribirme") { onIrInscripcion("Preparación laboral") }
        Text("Estos talleres son ejemplos académicos. Las inscripciones no son postulaciones reales.", style=MaterialTheme.typography.bodySmall)
    }
}
