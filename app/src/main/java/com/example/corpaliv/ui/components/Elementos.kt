package com.example.corpaliv.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ColumnaPantalla(titulo: String, descripcion: String = "", contenido: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)
    ) {
        Text(titulo, style = MaterialTheme.typography.headlineMedium)
        if (descripcion.isNotBlank()) {
            Spacer(Modifier.height(12.dp))
            Text(descripcion, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(Modifier.height(20.dp))
        contenido()
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun TarjetaInfo(titulo: String, texto: String, accion: String? = null, onClick: (() -> Unit)? = null) {
    Card(modifier = Modifier.fillMaxWidth().animateContentSize()) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text(texto)
            if (accion != null && onClick != null) {
                Spacer(Modifier.height(14.dp))
                Button(onClick=onClick, modifier=Modifier.fillMaxWidth()) { Text(accion) }
            }
        }
    }
    Spacer(Modifier.height(14.dp))
}

@Composable
fun CampoTexto(
    valor: String, cambiar: (String) -> Unit, etiqueta: String,
    error: String = "", tipo: KeyboardType = KeyboardType.Text,
    contrasena: Boolean = false, lineas: Int = 1, habilitado: Boolean = true
) {
    OutlinedTextField(
        value=valor, onValueChange=cambiar, label={Text(etiqueta)},
        keyboardOptions=KeyboardOptions(keyboardType=tipo),
        visualTransformation=if (contrasena) PasswordVisualTransformation() else VisualTransformation.None,
        isError=error.isNotEmpty(),
        trailingIcon=if (error.isNotEmpty()) { { Text("!", color=MaterialTheme.colorScheme.error) } } else null,
        supportingText=if (error.isNotEmpty()) { { Text(error) } } else null,
        modifier=Modifier.fillMaxWidth(), singleLine=lineas == 1,
        minLines=lineas, maxLines=if (lineas==1) 1 else 5,
        enabled=habilitado
    )
    Spacer(Modifier.height(10.dp))
}

@Composable
fun MensajeAnimado(mensaje: String) {
    AnimatedVisibility(visible=mensaje.isNotBlank()) {
        Text(mensaje, color=MaterialTheme.colorScheme.primary, modifier=Modifier.padding(vertical=14.dp))
    }
}
