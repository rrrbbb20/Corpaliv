
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onIrRegistroPersona: () -> Unit,
    onIrRegistroEmpresa: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecciona el tipo de cuenta que deseas registrar.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onIrRegistroPersona,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Persona natural")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onIrRegistroEmpresa,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Empresa")
        }
    }
}
