package com.uvg.gotavida.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Configuración de la base de datos local SQLite con Room para usuarios y notificaciones.
@Database(
    entities = [UsuarioEntity::class, NotificacionEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun notificacionDao(): NotificacionDao

    companion object {
        // Crea la instancia de la base de datos con migración destructiva en desarrollo
        fun crear(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "gotavida.db",
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
