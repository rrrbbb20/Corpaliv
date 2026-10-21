package com.example.corpaliv.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.example.corpaliv.data.local.dao.SolicitudDao
import com.example.corpaliv.data.local.dao.UsuarioDao
import com.example.corpaliv.data.local.model.Solicitud
import com.example.corpaliv.data.local.model.Usuario

@Database(entities = [Usuario::class, Solicitud::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun solicitudDao(): SolicitudDao

    companion object {
        // Mantiene los usuarios ya guardados al añadir la tabla solicitudes.
        private val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL("""
                    CREATE TABLE IF NOT EXISTS `solicitudes` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `usuarioId` INTEGER NOT NULL,
                        `tipo` TEXT NOT NULL,
                        `asunto` TEXT NOT NULL,
                        `descripcion` TEXT NOT NULL,
                        `estado` TEXT NOT NULL,
                        `fechaCreacion` INTEGER NOT NULL,
                        FOREIGN KEY(`usuarioId`) REFERENCES `usuarios`(`id`)
                            ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                """.trimIndent())
                connection.execSQL("CREATE INDEX IF NOT EXISTS `index_solicitudes_usuarioId` ON `solicitudes` (`usuarioId`)")
            }
        }

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "corpaliv_database"
            ).addMigrations(MIGRACION_1_2).build().also { INSTANCE = it }
        }
    }
}
