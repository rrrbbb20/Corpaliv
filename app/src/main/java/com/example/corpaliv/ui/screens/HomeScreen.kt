package com.example.corpaliv.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun HomeScreen(
    onIrRegistro: () -> Unit, onIrLogin: () -> Unit,
    onIrQuienesSomos: () -> Unit, onIrProgramas: () -> Unit,
    onIrDonaciones: () -> Unit, onIrSolicitudes: () -> Unit,
    onIrCuenta: () -> Unit, sesionIniciada: Boolean
) {
    var mostrarBienvenida by remember { mutableStateOf(true) }
    ColumnaPantalla("Transformamos vidas",
        "Apoyamos el desarrollo y la inclusión de personas con discapacidad múltiple.") {
        Button(onClick=onIrDonaciones, modifier=Modifier.fillMaxWidth()) { Text("Quiero colaborar") }
        Spacer(Modifier.height(18.dp))
        OutlinedButton(onClick={ mostrarBienvenida = !mostrarBienvenida }, modifier=Modifier.fillMaxWidth()) {
            Text(if (mostrarBienvenida) "Ocultar bienvenida" else "Mostrar bienvenida")
        }
        AnimatedVisibility(mostrarBienvenida) {
            Text("Conoce CORPALIV y descubre distintas formas de participar.",
                modifier=Modifier.padding(vertical=12.dp), color=MaterialTheme.colorScheme.primary)
        }
        Text("Descubre CORPALIV", style=MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        TarjetaInfo("Quiénes somos", "Conoce nuestra historia y propósito.", "Conocer", onIrQuienesSomos)
        TarjetaInfo("Nuestros programas", "Escuela Jan Van Dijk y Alba Lab.", "Ver programas", onIrProgramas)
        TarjetaInfo("Donaciones", "Descubre cómo apoyar nuestra labor (simulación académica).", "Ver alternativas", onIrDonaciones)
        Text("Mi cuenta", style=MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        if (sesionIniciada) {
            Button(onClick=onIrCuenta, modifier=Modifier.fillMaxWidth()) { Text("Ver mi cuenta") }
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick=onIrSolicitudes, modifier=Modifier.fillMaxWidth()) { Text("Mis solicitudes") }
        } else {
            OutlinedButton(onClick=onIrLogin, modifier=Modifier.fillMaxWidth()) { Text("Iniciar sesión") }
            TextButton(onClick=onIrRegistro, modifier=Modifier.fillMaxWidth()) { Text("Crear cuenta") }
        }
    }
}
