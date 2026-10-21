package com.example.corpaliv.utils

// Reglas independientes de Compose, fáciles de modificar en una defensa.
object Validaciones {
    fun correo(correo: String): String = when {
        correo.isBlank() -> "Ingresa el correo."
        !correo.trim().matches(Regex("""^[^\s@]+@[^\s@]+\.[^\s@]+$""")) -> "Correo no válido."
        else -> ""
    }
    fun nombre(nombre: String): String = if (nombre.trim().length < 2) "Ingresa al menos 2 caracteres." else ""
    fun rut(rut: String): String = if (!validarRut(rut)) "Ingresa un RUT válido." else ""
    fun contrasena(clave: String): String = if (clave.length < 8) "Mínimo 8 caracteres." else ""
    fun asunto(asunto: String): String = if (asunto.trim().length < 4) "Mínimo 4 caracteres." else ""
    fun descripcion(texto: String): String = if (texto.trim().length < 10) "Mínimo 10 caracteres." else ""
    fun telefono(valor: String): String = if (valor.length != 9 || !valor.all { it.isDigit() }) "Debe tener 9 dígitos." else ""
    fun edad(texto: String): String = if (texto.toIntOrNull() !in 1..120) "Edad entre 1 y 120." else ""
    fun monto(texto: String): String = if ((texto.toLongOrNull() ?: 0L) <= 0L) "Ingresa un monto mayor que cero." else ""
}
