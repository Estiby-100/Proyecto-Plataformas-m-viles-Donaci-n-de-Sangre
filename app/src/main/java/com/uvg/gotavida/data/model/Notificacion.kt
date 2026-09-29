package com.uvg.gotavida.data.model

enum class NotificacionTipo {
    SOLICITUD,
    CONFIRMACION,
    RECORDATORIO,
    VERIFICACION,
    ALERTA,
}

data class Notificacion(
    val id: String,
    val tipo: NotificacionTipo,
    val titulo: String,
    val subtitulo: String,
    val tiempoRelativo: String,
    val leida: Boolean,
)
