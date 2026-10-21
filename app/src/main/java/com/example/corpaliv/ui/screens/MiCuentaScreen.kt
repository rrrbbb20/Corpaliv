package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.TarjetaInfo
import com.example.corpaliv.viewmodel.SesionUiState

@Composable
fun MiCuentaScreen(sesion: SesionUiState, onMisSolicitudes: () -> Unit, onNuevaSolicitud: () -> Unit, onCerrarSesion: () -> Unit) {
    ColumnaPantalla("Mi cuenta", "Sesión local iniciada.") {
        TarjetaInfo("Datos personales", "Nombre: ${sesion.nombre}\nCorreo: ${sesion.correo}\nRUT: ${sesion.rut}")
        Button(onClick=onNuevaSolicitud, modifier=Modifier.fillMaxWidth()) { Text("Crear solicitud") }
        Spacer(Modifier.height(10.dp))
        OutlinedButton(onClick=onMisSolicitudes, modifier=Modifier.fillMaxWidth()) { Text("Ver mis solicitudes") }
        Spacer(Modifier.height(20.dp))
        TextButton(onClick=onCerrarSesion, modifier=Modifier.fillMaxWidth()) { Text("Cerrar sesión") }
    }
}
