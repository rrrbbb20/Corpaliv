package com.example.corpaliv.ui.screens

import androidx.compose.runtime.Composable
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun ProgramasScreen(onIrEscuela: () -> Unit, onIrAlbaLab: () -> Unit) {
    ColumnaPantalla("Nuestros programas", "Conoce las iniciativas de CORPALIV para la inclusión y participación.") {
        TarjetaInfo("Escuela Especial Jan Van Dijk", "Programa educativo especializado para estudiantes con discapacidad múltiple.", "Conocer la escuela", onIrEscuela)
        TarjetaInfo("Alba Lab", "Programa sociolaboral con formación y oportunidades de inclusión laboral.", "Conocer Alba Lab", onIrAlbaLab)
    }
}
