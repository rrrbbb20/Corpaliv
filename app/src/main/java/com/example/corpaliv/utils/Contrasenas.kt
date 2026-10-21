package com.example.corpaliv.utils

import android.os.Build
import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

// PBKDF2 con salt diferente por usuario. Nunca guardamos la contraseña original.
object Contrasenas {
    fun crearHash(clave: String): String {
        val algoritmo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            "PBKDF2WithHmacSHA256" else "PBKDF2WithHmacSHA1"
        val iteraciones = 120_000
        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) }
        val spec = PBEKeySpec(clave.toCharArray(), salt, iteraciones, 256)
        return try {
            val hash = SecretKeyFactory.getInstance(algoritmo).generateSecret(spec).encoded
            "$algoritmo:$iteraciones:${Base64.encodeToString(salt, Base64.NO_WRAP)}:${Base64.encodeToString(hash, Base64.NO_WRAP)}"
        } finally { spec.clearPassword() }
    }

    fun verificar(clave: String, guardado: String): Boolean {
        val partes = guardado.split(":")
        if (partes.size != 4) return false
        val algoritmo = partes[0]
        if (algoritmo !in listOf("PBKDF2WithHmacSHA256", "PBKDF2WithHmacSHA1")) return false
        val iteraciones = partes[1].toIntOrNull() ?: return false
        if (iteraciones !in 10_000..1_000_000) return false
        return try {
            val salt = Base64.decode(partes[2], Base64.NO_WRAP)
            val hashOriginal = Base64.decode(partes[3], Base64.NO_WRAP)
            if (salt.size != 16 || hashOriginal.size != 32) return false
            val spec = PBEKeySpec(clave.toCharArray(), salt, iteraciones, hashOriginal.size * 8)
            try {
                val calculado = SecretKeyFactory.getInstance(algoritmo).generateSecret(spec).encoded
                MessageDigest.isEqual(hashOriginal, calculado)
            } finally { spec.clearPassword() }
        } catch (_: IllegalArgumentException) { false }
    }
}
