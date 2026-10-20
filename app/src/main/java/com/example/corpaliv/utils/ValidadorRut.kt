
package com.example.corpaliv.utils

fun validarRut(rut: String): Boolean {

    // Quitamos puntos, guion y espacios
    val rutLimpio = rut.replace(".", "")
        .replace("-", "")
        .replace(" ", "")
        .uppercase()

    // Verificamos el formato y la longitud
    if (!rutLimpio.matches(Regex("^[0-9]{1,8}[0-9K]$"))) {
        return false
    }

    // Separamos el número del dígito verificador
    val numero = rutLimpio.dropLast(1)
    val digitoVerificador = rutLimpio.last()

    // No aceptamos un número igual a cero
    if (numero.toLongOrNull() == null || numero.toLong() == 0L) {
        return false
    }

    var suma = 0
    var multiplicador = 2

    // Recorremos el número desde la derecha
    for (digito in numero.reversed()) {
        suma += digito.digitToInt() * multiplicador

        multiplicador++

        if (multiplicador > 7) {
            multiplicador = 2
        }
    }

    val resto = 11 - (suma % 11)

    val digitoCalculado = when (resto) {
        11 -> '0'
        10 -> 'K'
        else -> resto.digitToChar()
    }

    return digitoVerificador == digitoCalculado
}
