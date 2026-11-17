package com.uvg.gotavida.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificacionDao {

    @Query("SELECT * FROM notificaciones WHERE rol = :rol ORDER BY timestamp DESC")
    fun observarPorRol(rol: String): Flow<List<NotificacionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(notificacion: NotificacionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarias(notificaciones: List<NotificacionEntity>)

    @Query("UPDATE notificaciones SET leida = 1 WHERE id = :id")
    suspend fun marcarLeida(id: String)

    @Query("UPDATE notificaciones SET leida = 1 WHERE rol = :rol")
    suspend fun marcarTodasLeidas(rol: String)

    @Query("SELECT COUNT(*) FROM notificaciones")
    suspend fun contar(): Int
}
