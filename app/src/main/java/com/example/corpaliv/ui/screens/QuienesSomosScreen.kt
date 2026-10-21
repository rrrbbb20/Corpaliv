package com.example.corpaliv.ui.screens

import androidx.compose.runtime.Composable
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun QuienesSomosScreen() {
    ColumnaPantalla("Quiénes somos",
        "CORPALIV es una organización sin fines de lucro dedicada a promover la autonomía, participación e inclusión social y laboral de personas con discapacidad múltiple.") {
        TarjetaInfo("Nuestro propósito", "Promover oportunidades de desarrollo e inclusión, acompañando a personas y familias.")
        TarjetaInfo("Nuestro trabajo", "A través de la Escuela Especial Jan Van Dijk y Alba Lab se desarrollan iniciativas educativas, formativas y de vinculación laboral.")
        TarjetaInfo("Colaboración", "La aplicación organiza información sobre programas y oportunidades de colaboración. Las acciones realizadas aquí son demostraciones académicas.")
    }
}
