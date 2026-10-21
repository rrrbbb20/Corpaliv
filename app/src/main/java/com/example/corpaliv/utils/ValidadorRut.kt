package com.example.corpaliv.utils

fun validarRut(rut: String): Boolean {
    val limpio = rut.replace(".", "").replace("-", "").replace(" ", "").uppercase()
    if (!limpio.matches(Regex("^[0-9]{1,8}[0-9K]$"))) return false
    val numero = limpio.dropLast(1)
    if ((numero.toLongOrNull() ?: 0L) == 0L) return false
    var suma = 0
    var multiplicador = 2
    for (digito in numero.reversed()) {
        suma += digito.digitToInt() * multiplicador
        multiplicador = if (multiplicador == 7) 2 else multiplicador + 1
    }
    val resultado = when (val resto = 11 - (suma % 11)) {
        11 -> '0'
        10 -> 'K'
        else -> resto.digitToChar()
    }
    return limpio.last() == resultado
}
