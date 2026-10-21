package com.example.corpaliv.ui.screens

import androidx.compose.runtime.Composable
import com.example.corpaliv.ui.components.ColumnaPantalla
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun DonacionesScreen(onIrSimularDonacion: () -> Unit, onIrColaboracionEmpresa: () -> Unit) {
    ColumnaPantalla("Colabora con CORPALIV", "Tu apoyo puede contribuir al desarrollo de sus programas.") {
        TarjetaInfo("Donaciones de personas", "Selecciona un monto y completa una donación completamente ficticia.", "Simular donación", onIrSimularDonacion)
        TarjetaInfo("Colaboración empresarial", "Explora alternativas de vinculación y registra una propuesta de prueba.", "Solicitar colaboración", onIrColaboracionEmpresa)
    }
}
