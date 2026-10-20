
package com.example.corpaliv.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuNavegacion(
    onInicio: () -> Unit,
    onQuienesSomos: () -> Unit,
    onProgramas: () -> Unit,
    onDonaciones: () -> Unit,
    onMiCuenta: () -> Unit
) {
    ModalDrawerSheet {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "CORPALIV",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Text(
            text = "Transformamos vidas",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = false,
            onClick = onInicio
        )

        NavigationDrawerItem(
            label = { Text("Quiénes somos") },
            selected = false,
            onClick = onQuienesSomos
        )

        NavigationDrawerItem(
            label = { Text("Programas") },
            selected = false,
            onClick = onProgramas
        )

        NavigationDrawerItem(
            label = { Text("Donaciones") },
            selected = false,
            onClick = onDonaciones
        )

        HorizontalDivider()

        NavigationDrawerItem(
            label = { Text("Mi cuenta") },
            selected = false,
            onClick = onMiCuenta
        )
    }
}
