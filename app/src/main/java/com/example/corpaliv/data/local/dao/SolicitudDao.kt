package com.example.corpaliv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.corpaliv.data.local.model.Solicitud
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {
    @Insert suspend fun insertar(solicitud: Solicitud): Long

    @Query("SELECT * FROM solicitudes WHERE usuarioId = :usuarioId ORDER BY fechaCreacion DESC, id DESC")
    fun observarPorUsuario(usuarioId: Int): Flow<List<Solicitud>>

    @Query("UPDATE solicitudes SET estado = :estado WHERE id = :solicitudId AND usuarioId = :usuarioId")
    suspend fun actualizarEstado(solicitudId: Int, usuarioId: Int, estado: String): Int
}
