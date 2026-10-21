package com.example.corpaliv.ui.screens

import androidx.compose.runtime.Composable
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun EscuelaScreen() {
    ColumnaPantalla("Escuela Especial Jan Van Dijk", "Conoce el trabajo educativo con estudiantes con discapacidad múltiple.") {
        TarjetaInfo("¿Qué es la escuela?", "Forma parte de las iniciativas de CORPALIV y orienta su labor al desarrollo y aprendizaje.")
        TarjetaInfo("Enfoque educativo", "Busca favorecer comunicación, autonomía, participación y desarrollo integral.")
        TarjetaInfo("Nuestra comunidad", "El acompañamiento considera colaboración entre estudiantes, familias y equipos profesionales.")
    }
}
