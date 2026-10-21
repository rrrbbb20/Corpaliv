package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.corpaliv.ui.components.CampoTexto
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.MensajeAnimado
import com.example.corpaliv.utils.Validaciones
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InscripcionTallerScreen(nombreTaller: String) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var acepta by remember { mutableStateOf(false) }
    var novedades by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }
    var errorNombre by remember { mutableStateOf("") }
    var errorCorreo by remember { mutableStateOf("") }
    var errorEdad by remember { mutableStateOf("") }
    var errorFecha by remember { mutableStateOf("") }
    var errorAcepta by remember { mutableStateOf("") }
    var mostrarCalendario by remember { mutableStateOf(false) }
    val fechaState = rememberDatePickerState()
    val fechaMillis = fechaState.selectedDateMillis
    val fechaTexto = fechaMillis?.let { SimpleDateFormat("dd/MM/yyyy",Locale("es","CL")).format(Date(it)) } ?: ""

    if (mostrarCalendario) {
        DatePickerDialog(
            onDismissRequest={mostrarCalendario=false},
            confirmButton={ TextButton(onClick={mostrarCalendario=false}) { Text("Aceptar") } },
            dismissButton={ TextButton(onClick={mostrarCalendario=false}) { Text("Cancelar") } }
        ) { DatePicker(state=fechaState) }
    }
    ColumnaPantalla("Inscripción a taller", "Taller seleccionado: $nombreTaller. Inscripción solamente académica.") {
        CampoTexto(nombre, {nombre=it; errorNombre=""; mensaje=""}, "Nombre del participante", errorNombre)
        CampoTexto(correo, {correo=it; errorCorreo=""; mensaje=""}, "Correo ficticio", errorCorreo, KeyboardType.Email)
        CampoTexto(edad, {edad=it.filter(Char::isDigit).take(3); errorEdad=""; mensaje=""}, "Edad", errorEdad, KeyboardType.Number)
        OutlinedButton(onClick={mostrarCalendario=true; errorFecha=""}, modifier=Modifier.fillMaxWidth()) {
            Text(if (fechaTexto.isBlank()) "Seleccionar fecha de inscripción" else "Fecha: $fechaTexto")
        }
        if (errorFecha.isNotBlank()) Text(errorFecha, color=MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(10.dp))
        Row(verticalAlignment=Alignment.CenterVertically) {
            Checkbox(checked=acepta, onCheckedChange={acepta=it;errorAcepta=""})
            Text("Acepto las condiciones de la simulación")
        }
        if (errorAcepta.isNotBlank()) Text(errorAcepta, color=MaterialTheme.colorScheme.error)
        Row(verticalAlignment=Alignment.CenterVertically) {
            Text("Recibir novedades", modifier=Modifier.weight(1f))
            Switch(checked=novedades, onCheckedChange={novedades=it})
        }
        Spacer(Modifier.height(14.dp))
        Button(onClick={
            errorNombre=Validaciones.nombre(nombre)
            errorCorreo=Validaciones.correo(correo)
            errorEdad=Validaciones.edad(edad)
            errorFecha=if (fechaMillis==null) "Selecciona una fecha." else ""
            errorAcepta=if (!acepta) "Debes aceptar las condiciones." else ""
            mensaje=if (listOf(errorNombre,errorCorreo,errorEdad,errorFecha,errorAcepta).all(String::isEmpty))
                "Formulario válido. La inscripción no se ha guardado ni enviado." else ""
        }, modifier=Modifier.fillMaxWidth()) { Text("Confirmar inscripción de prueba") }
        MensajeAnimado(mensaje)
        Text("No utilices datos reales.", style=MaterialTheme.typography.bodySmall)
    }
}
