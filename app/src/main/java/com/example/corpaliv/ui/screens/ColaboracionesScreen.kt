package com.example.corpaliv.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.corpaliv.ui.components.CampoTexto
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.MensajeAnimado
import com.example.corpaliv.utils.Validaciones
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColaboracionesScreen() {
    var empresa by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var expandido by remember { mutableStateOf(false) }
    var errorEmpresa by remember { mutableStateOf("") }
    var errorTelefono by remember { mutableStateOf("") }
    var errorTipo by remember { mutableStateOf("") }
    var errorDescripcion by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    // La fotografía es OPCIONAL y solo se utiliza en esta simulación.
    // TakePicturePreview devuelve una miniatura, no una foto a tamaño completo.
    val context = LocalContext.current
    var fotoCamara by remember { mutableStateOf<Bitmap?>(null) }
    var uriGaleria by remember { mutableStateOf<Uri?>(null) }
    var fotoGaleria by remember { mutableStateOf<Bitmap?>(null) }
    var mensajeImagen by remember { mutableStateOf("") }

    val camara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { foto ->
        if (foto != null) {
            fotoCamara = foto
            fotoGaleria = null
            uriGaleria = null
            mensajeImagen = "Fotografía tomada. Puedes revisar la vista previa."
            mensaje = ""
        } else {
            mensajeImagen = "No se tomó ninguna fotografía."
        }
    }

    // GetContent permite elegir una imagen sin pedir acceso a toda la galería.
    val galeria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            uriGaleria = uri
            fotoCamara = null
            fotoGaleria = null
            mensajeImagen = "Cargando imagen seleccionada..."
            mensaje = ""
        } else {
            mensajeImagen = "No se seleccionó otra imagen."
        }
    }

    LaunchedEffect(uriGaleria) {
        val uri = uriGaleria ?: return@LaunchedEffect
        fotoGaleria = withContext(Dispatchers.IO) {
            try {
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    BitmapFactory.decodeStream(
                        stream,
                        null,
                        BitmapFactory.Options().apply { inSampleSize = 4 }
                    )
                }
            } catch (_: Exception) {
                null
            }
        }
        mensajeImagen = if (fotoGaleria == null) {
            "No se pudo mostrar la imagen. Intenta con otra."
        } else {
            "Imagen seleccionada. Puedes revisar la vista previa."
        }
    }

    val imagenAdjunta = fotoCamara ?: fotoGaleria
    val tipos = listOf(
        "Donación de recursos",
        "Apoyo a talleres",
        "Oportunidades laborales",
        "Otra colaboración"
    )

    ColumnaPantalla(
        "Colaboración empresarial",
        "Registra una propuesta ficticia. No se envía a CORPALIV."
    ) {
        CampoTexto(
            empresa, { empresa = it; errorEmpresa = ""; mensaje = "" },
            "Nombre de la empresa", errorEmpresa
        )
        CampoTexto(
            telefono,
            { telefono = it.filter(Char::isDigit).take(9); errorTelefono = ""; mensaje = "" },
            "Teléfono de contacto", errorTelefono, KeyboardType.Phone
        )
        ExposedDropdownMenuBox(expanded = expandido, onExpandedChange = { expandido = !expandido }) {
            OutlinedTextField(
                value = tipo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de colaboración") },
                isError = errorTipo.isNotEmpty(),
                supportingText = if (errorTipo.isNotBlank()) { { Text(errorTipo) } } else null,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                tipos.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = { tipo = opcion; expandido = false; errorTipo = ""; mensaje = "" }
                    )
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        CampoTexto(
            descripcion,
            { descripcion = it; errorDescripcion = ""; mensaje = "" },
            "Describe tu propuesta", errorDescripcion, lineas = 3
        )

        // Recursos nativos integrados en el formulario, no en otra pantalla.
        Text("Imagen de los materiales (opcional)", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        Text(
            "Puedes fotografiar o seleccionar una imagen de los materiales ofrecidos.",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(12.dp))
        Button(onClick = { camara.launch(null) }, modifier = Modifier.fillMaxWidth()) {
            Text("Tomar fotografía")
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = { galeria.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
            Text("Seleccionar de galería")
        }

        if (imagenAdjunta != null) {
            Spacer(Modifier.height(14.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Vista previa del adjunto", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(8.dp))
                    Image(
                        bitmap = imagenAdjunta.asImageBitmap(),
                        contentDescription = "Fotografía de los materiales de la colaboración",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 260.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    TextButton(onClick = {
                        fotoCamara = null
                        fotoGaleria = null
                        uriGaleria = null
                        mensajeImagen = "Imagen quitada de la propuesta."
                        mensaje = ""
                    }) { Text("Quitar imagen") }
                }
            }
        }
        if (mensajeImagen.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(mensajeImagen, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            errorEmpresa = Validaciones.nombre(empresa)
            errorTelefono = Validaciones.telefono(telefono)
            errorTipo = if (tipo.isBlank()) "Selecciona un tipo." else ""
            errorDescripcion = Validaciones.descripcion(descripcion)
            mensaje = if (listOf(errorEmpresa, errorTelefono, errorTipo, errorDescripcion).all(String::isEmpty)) {
                if (imagenAdjunta != null) {
                    "Propuesta de prueba validada con imagen adjunta. No se ha guardado ni enviado."
                } else {
                    "Propuesta de prueba validada sin imagen. No se ha guardado ni enviado."
                }
            } else ""
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Validar propuesta de prueba")
        }
        MensajeAnimado(mensaje)
        Text(
            "Simulación académica: utiliza datos e imágenes ficticios. " +
                "La fotografía solo se muestra en este formulario; no se guarda ni se envía.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
