package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.corpaliv.ui.components.CampoTexto
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.MensajeAnimado
import com.example.corpaliv.utils.Validaciones

@Composable
fun SimularDonacionScreen() {
    var montoSeleccionado by remember { mutableStateOf("5000") }
    var otroMonto by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var tarjeta by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var errorMonto by remember { mutableStateOf("") }
    var errorNombre by remember { mutableStateOf("") }
    var errorCiudad by remember { mutableStateOf("") }
    var errorDireccion by remember { mutableStateOf("") }
    var errorTarjeta by remember { mutableStateOf("") }
    var errorCvc by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val monto = if (montoSeleccionado=="otro") otroMonto else montoSeleccionado

    ColumnaPantalla("Donación simulada", "Selecciona un monto y utiliza únicamente datos ficticios. No hay cobros ni almacenamiento bancario.") {
        Text("Monto de la donación", style=MaterialTheme.typography.titleLarge)
        listOf("3000" to "3.000 CLP", "5000" to "5.000 CLP", "10000" to "10.000 CLP", "otro" to "Otro monto").forEach { (valor, titulo) ->
            Row {
                RadioButton(selected=montoSeleccionado==valor, onClick={montoSeleccionado=valor;errorMonto="";mensaje=""})
                Text(titulo, modifier=Modifier.padding(top=12.dp))
            }
        }
        if (montoSeleccionado=="otro") {
            CampoTexto(otroMonto, {otroMonto=it.filter(Char::isDigit).take(9);errorMonto="";mensaje=""}, "Otro monto CLP", errorMonto, KeyboardType.Number)
        } else if (errorMonto.isNotBlank()) Text(errorMonto, color=MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(12.dp))
        Text("Datos del donante", style=MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(10.dp))
        CampoTexto(nombre, {nombre=it;errorNombre="";mensaje=""}, "Nombre ficticio", errorNombre)
        CampoTexto(ciudad, {ciudad=it;errorCiudad="";mensaje=""}, "Ciudad", errorCiudad)
        CampoTexto(direccion, {direccion=it;errorDireccion="";mensaje=""}, "Dirección ficticia", errorDireccion)
        Text("Pago de demostración: NO ingreses tarjetas reales.", style=MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))
        CampoTexto(tarjeta, {tarjeta=it.filter(Char::isDigit).take(12);errorTarjeta="";mensaje=""}, "Código ficticio de 12 dígitos", errorTarjeta, KeyboardType.Number)
        CampoTexto(cvc, {cvc=it.filter(Char::isDigit).take(3);errorCvc="";mensaje=""}, "Código ficticio de 3 dígitos", errorCvc, KeyboardType.Number)
        Button(onClick={
            errorMonto=Validaciones.monto(monto)
            errorNombre=Validaciones.nombre(nombre)
            errorCiudad=if (ciudad.isBlank()) "Ingresa la ciudad." else ""
            errorDireccion=if (direccion.isBlank()) "Ingresa la dirección ficticia." else ""
            errorTarjeta=if (tarjeta.length!=12) "El código ficticio debe tener 12 dígitos." else ""
            errorCvc=if (cvc.length!=3) "El código ficticio debe tener 3 dígitos." else ""
            mensaje=if (listOf(errorMonto,errorNombre,errorCiudad,errorDireccion,errorTarjeta,errorCvc).all(String::isEmpty))
                "Donación de $monto CLP confirmada SOLO como demostración. No se realizó ningún cobro." else ""
        }, modifier=Modifier.fillMaxWidth()) { Text("Confirmar donación simulada") }
        MensajeAnimado(mensaje)
    }
}
