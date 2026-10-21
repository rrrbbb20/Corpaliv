
package com.example.corpaliv.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColoresCorpaliv = lightColorScheme(

    // Botones y elementos principales
    primary = VerdePrincipal,
    onPrimary = Color.White,

    // Elementos secundarios
    secondary = VerdePrincipal,
    onSecondary = Color.White,

    // Contenedores suaves
    primaryContainer = VerdeClaro,
    onPrimaryContainer = TextoOscuro,

    secondaryContainer = VerdeClaro,
    onSecondaryContainer = TextoOscuro,

    // Fondo general
    background = FondoClaro,
    onBackground = TextoOscuro,

    // Tarjetas y superficies
    surface = FondoClaro,
    onSurface = TextoOscuro
)

@Composable
fun CorpalivTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColoresCorpaliv,
        typography = Typography,
        content = content
    )
}
