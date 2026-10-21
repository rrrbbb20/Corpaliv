package com.example.corpaliv.data.repository

import com.example.corpaliv.data.local.dao.UsuarioDao
import com.example.corpaliv.data.local.model.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    suspend fun registrarUsuario(usuario: Usuario) = usuarioDao.insertar(usuario)
    suspend fun buscarPorCorreo(correo: String) = usuarioDao.buscarPorCorreo(correo)
    suspend fun buscarPorRut(rut: String) = usuarioDao.buscarPorRut(rut)
    suspend fun buscarPorId(id: Int) = usuarioDao.buscarPorId(id)
}
