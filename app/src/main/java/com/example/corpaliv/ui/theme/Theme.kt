package com.example.corpaliv.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColoresCorpaliv = lightColorScheme(
    primary = VerdePrincipal,
    onPrimary = Color.White,
    secondary = VerdePrincipal,
    onSecondary = Color.White,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = TextoOscuro,
    secondaryContainer = VerdeClaro,
    onSecondaryContainer = TextoOscuro,
    background = FondoClaro,
    onBackground = TextoOscuro,
    surface = FondoClaro,
    onSurface = TextoOscuro
)

@Composable
fun CorpalivTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = ColoresCorpaliv, typography = Typography, content = content)
}
