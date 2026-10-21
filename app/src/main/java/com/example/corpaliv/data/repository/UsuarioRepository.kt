
package com.example.corpaliv.data.repository

import com.example.corpaliv.data.local.dao.UsuarioDao
import com.example.corpaliv.data.local.model.Usuario

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun registrarUsuario(usuario: Usuario): Long {
        return usuarioDao.insertar(usuario)
    }

    suspend fun buscarPorCorreo(correo: String): Usuario? {
        return usuarioDao.buscarPorCorreo(correo)
    }

    suspend fun buscarPorRut(rut: String): Usuario? {
        return usuarioDao.buscarPorRut(rut)
    }

    suspend fun buscarPorId(id: Int): Usuario? {
        return usuarioDao.buscarPorId(id)
    }
}
