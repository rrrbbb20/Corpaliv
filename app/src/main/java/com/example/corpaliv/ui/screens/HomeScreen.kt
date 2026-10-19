
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedButton
@Composable
fun HomeScreen(
    onIrRegistro: () -> Unit,
    onIrLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido a CORPALIV",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Transformamos vidas promoviendo la inclusión social y laboral.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onIrRegistro
        ) {
            Text("Quiero colaborar Registrarme")
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onIrLogin
        ) {
            Text("Iniciar sesión")
        }
    }
}
