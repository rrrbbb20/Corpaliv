package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.corpaliv.ui.components.CampoTexto
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.MensajeAnimado
import com.example.corpaliv.viewmodel.SolicitudViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaSolicitudScreen(viewModel: SolicitudViewModel, onVerSolicitudes: () -> Unit) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()
    val tipos = listOf("Consulta general", "Información sobre programas", "Voluntariado")
    var desplegado by remember { mutableStateOf(false) }
    ColumnaPantalla("Nueva solicitud", "Este formulario guarda una solicitud de prueba en el dispositivo. No se envía a CORPALIV.") {
        ExposedDropdownMenuBox(expanded=desplegado, onExpandedChange={desplegado=!desplegado}) {
            OutlinedTextField(
                value=estado.tipo, onValueChange={}, readOnly=true,
                label={Text("Tipo de solicitud")},
                trailingIcon={ExposedDropdownMenuDefaults.TrailingIcon(expanded=desplegado)},
                modifier=Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded=desplegado, onDismissRequest={desplegado=false}) {
                tipos.forEach { tipo ->
                    DropdownMenuItem(text={Text(tipo)}, onClick={viewModel.cambiarTipo(tipo);desplegado=false})
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        CampoTexto(estado.asunto, viewModel::cambiarAsunto, "Asunto", estado.errorAsunto)
        CampoTexto(estado.descripcion, viewModel::cambiarDescripcion, "Describe tu consulta", estado.errorDescripcion, lineas=3)
        Row(modifier=Modifier.fillMaxWidth()) {
            Checkbox(checked=estado.aceptaCondiciones, onCheckedChange=viewModel::cambiarCondiciones)
            Text("Acepto que esta solicitud es una simulación académica.", modifier=Modifier.padding(top=10.dp))
        }
        if (estado.errorCondiciones.isNotBlank()) Text(estado.errorCondiciones, color=MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(16.dp))
        Button(onClick=viewModel::guardar, enabled=!estado.guardando, modifier=Modifier.fillMaxWidth()) {
            Text(if (estado.guardando) "Guardando..." else "Guardar solicitud")
        }
        MensajeAnimado(estado.mensaje)
        OutlinedButton(onClick=onVerSolicitudes, modifier=Modifier.fillMaxWidth()) { Text("Ver mis solicitudes") }
    }
}
