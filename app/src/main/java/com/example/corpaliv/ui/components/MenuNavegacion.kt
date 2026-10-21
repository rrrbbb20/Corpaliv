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
    onMiCuenta: () -> Unit,
    onSolicitudes: () -> Unit,
    conectado: Boolean
) {
    ModalDrawerSheet {
        Spacer(Modifier.height(24.dp))
        Text("CORPALIV", style=MaterialTheme.typography.headlineMedium,
            modifier=Modifier.padding(horizontal=20.dp))
        Text("Transformamos vidas", modifier=Modifier.padding(horizontal=20.dp))
        Spacer(Modifier.height(24.dp))
        HorizontalDivider()
        NavigationDrawerItem(label={ Text("Inicio") }, selected=false, onClick=onInicio)
        NavigationDrawerItem(label={ Text("Quiénes somos") }, selected=false, onClick=onQuienesSomos)
        NavigationDrawerItem(label={ Text("Programas") }, selected=false, onClick=onProgramas)
        NavigationDrawerItem(label={ Text("Donaciones") }, selected=false, onClick=onDonaciones)
        HorizontalDivider()
        NavigationDrawerItem(label={ Text("Mis solicitudes") }, selected=false, onClick=onSolicitudes)
        NavigationDrawerItem(label={ Text(if (conectado) "Mi cuenta" else "Iniciar sesión") }, selected=false, onClick=onMiCuenta)
    }
}
