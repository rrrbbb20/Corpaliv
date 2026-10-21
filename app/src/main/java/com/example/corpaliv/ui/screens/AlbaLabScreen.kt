package com.example.corpaliv.ui.screens

import androidx.compose.runtime.Composable
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun AlbaLabScreen(onIrTalleres: () -> Unit) {
    ColumnaPantalla("Alba Lab", "Programa sociolaboral de CORPALIV.") {
        TarjetaInfo("¿Qué es Alba Lab?", "Promueve habilidades sociolaborales y oportunidades de participación e inclusión laboral.")
        TarjetaInfo("Formación y talleres", "Explora talleres de demostración orientados al aprendizaje de habilidades para la vida y el trabajo.", "Ver talleres disponibles", onIrTalleres)
        TarjetaInfo("Inclusión sociolaboral", "La formación y colaboración con distintos actores busca impulsar oportunidades de participación.")
    }
}
