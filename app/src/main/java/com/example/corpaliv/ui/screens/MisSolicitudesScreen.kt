package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.MensajeAnimado
import com.example.corpaliv.viewmodel.SolicitudViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MisSolicitudesScreen(viewModel: SolicitudViewModel, onNuevaSolicitud: () -> Unit) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()
    ColumnaPantalla("Mis solicitudes", "Se guardan en Room y se muestran solo para esta cuenta.") {
        Button(onClick=onNuevaSolicitud, modifier=Modifier.fillMaxWidth()) { Text("Crear nueva solicitud") }
        Spacer(Modifier.height(18.dp))
        if (estado.solicitudes.isEmpty()) Text("Todavía no tienes solicitudes guardadas.")
        estado.solicitudes.forEach { solicitud ->
            Card(modifier=Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(solicitud.asunto, style=MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(6.dp))
                    Text("Tipo: ${solicitud.tipo}")
                    Text("Estado: ${solicitud.estado}", color=MaterialTheme.colorScheme.primary)
                    Text("Fecha: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es", "CL")).format(Date(solicitud.fechaCreacion))}")
                    Spacer(Modifier.height(8.dp))
                    Text(solicitud.descripcion)
                    if (solicitud.estado != "Respondida") {
                        Spacer(Modifier.height(12.dp))
                        OutlinedButton(onClick={viewModel.avanzarEstado(solicitud)}) {
                            Text("Avanzar estado (simulación)")
                        }
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
        }
        MensajeAnimado(estado.mensaje)
        Text("Los estados son simulados; no hay respuesta de la organización.", style=MaterialTheme.typography.bodySmall)
    }
}
