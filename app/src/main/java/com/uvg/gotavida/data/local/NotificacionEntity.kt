package com.uvg.gotavida.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

// Entidad de Room para representar las notificaciones almacenadas en el teléfono.
@Entity(tableName = "notificaciones")
data class NotificacionEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val rol: String,
    val tipo: String,
    val titulo: String,
    val subtitulo: String,
    val tiempoRelativo: String,
    val leida: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
