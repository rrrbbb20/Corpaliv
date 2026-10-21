package com.example.corpaliv.data.repository

import com.example.corpaliv.data.local.dao.SolicitudDao
import com.example.corpaliv.data.local.model.Solicitud

class SolicitudRepository(private val dao: SolicitudDao) {
    suspend fun crear(solicitud: Solicitud) = dao.insertar(solicitud)
    fun observar(usuarioId: Int) = dao.observarPorUsuario(usuarioId)
    suspend fun actualizarEstado(id: Int, usuarioId: Int, estado: String) =
        dao.actualizarEstado(id, usuarioId, estado)
}
