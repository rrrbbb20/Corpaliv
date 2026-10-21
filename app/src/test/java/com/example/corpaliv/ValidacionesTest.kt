package com.example.corpaliv

import com.example.corpaliv.utils.Validaciones
import com.example.corpaliv.utils.validarRut
import org.junit.Assert.*
import org.junit.Test

class ValidacionesTest {
    @Test fun validaRutDeEjemplo() {
        assertTrue(validarRut("12.345.678-5"))
        assertFalse(validarRut("12.345.678-9"))
    }
    @Test fun validaCorreo() {
        assertEquals("", Validaciones.correo("demo@ejemplo.cl"))
        assertTrue(Validaciones.correo("correo-sin-arroba").isNotEmpty())
    }
    @Test fun validaAsuntoYDescripcion() {
        assertTrue(Validaciones.asunto("abc").isNotEmpty())
        assertEquals("", Validaciones.asunto("Consulta"))
        assertTrue(Validaciones.descripcion("corto").isNotEmpty())
    }
    @Test fun validaEdad() {
        assertEquals("", Validaciones.edad("18"))
        assertTrue(Validaciones.edad("200").isNotEmpty())
    }
}
