package com.uvg.gotavida.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// DAO para consultas de notificaciones guardadas en el teléfono.
@Dao
interface NotificacionDao {

    // Obtiene el flujo de notificaciones ordenadas por fecha para el rol especificado
    @Query("SELECT * FROM notificaciones WHERE rol = :rol ORDER BY timestamp DESC")
    fun observarPorRol(rol: String): Flow<List<NotificacionEntity>>

    // Inserta o actualiza una notificación
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(notificacion: NotificacionEntity)

    // Inserta una lista completa de semillas iniciales
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarias(notificaciones: List<NotificacionEntity>)

    // Marca una notificación específica como leída
    @Query("UPDATE notificaciones SET leida = 1 WHERE id = :id")
    suspend fun marcarLeida(id: String)

    // Marca todas las notificaciones de un rol como leídas
    @Query("UPDATE notificaciones SET leida = 1 WHERE rol = :rol")
    suspend fun marcarTodasLeidas(rol: String)

    // Cuenta cuántas notificaciones existen en la base local
    @Query("SELECT COUNT(*) FROM notificaciones")
    suspend fun contar(): Int
}
