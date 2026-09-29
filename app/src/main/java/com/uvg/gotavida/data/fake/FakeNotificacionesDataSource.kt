package com.uvg.gotavida.data.fake

import com.uvg.gotavida.data.model.Notificacion
import com.uvg.gotavida.data.model.NotificacionTipo

object FakeNotificacionesDataSource {
    val donante = listOf(
        Notificacion(
            id = "d1",
            tipo = NotificacionTipo.SOLICITUD,
            titulo = "Nueva solicitud O+ cerca de ti · Hospital Roosevelt",
            subtitulo = "Sangre tipo O+ con urgencia alta",
            tiempoRelativo = "hace 10 min",
            leida = false,
        ),
        Notificacion(
            id = "d2",
            tipo = NotificacionTipo.CONFIRMACION,
            titulo = "La institución confirmó tu disponibilidad",
            subtitulo = "Hospital Herrera Llerandi — Zona 10",
            tiempoRelativo = "hace 2 h",
            leida = false,
        ),
        Notificacion(
            id = "d3",
            tipo = NotificacionTipo.RECORDATORIO,
            titulo = "Ya puedes volver a donar",
            subtitulo = "Han pasado 56 días desde tu última donación",
            tiempoRelativo = "hace 1 día",
            leida = true,
        ),
    )

    val medica = listOf(
        Notificacion(
            id = "m1",
            tipo = NotificacionTipo.CONFIRMACION,
            titulo = "Un donante confirmó disponibilidad para tu solicitud",
            subtitulo = "Solicitud activa · O+ · Zona 11",
            tiempoRelativo = "hace 5 min",
            leida = false,
        ),
        Notificacion(
            id = "m2",
            tipo = NotificacionTipo.VERIFICACION,
            titulo = "Tu cuenta institucional fue verificada",
            subtitulo = "Ya puedes publicar solicitudes",
            tiempoRelativo = "hace 1 día",
            leida = true,
        ),
        Notificacion(
            id = "m3",
            tipo = NotificacionTipo.ALERTA,
            titulo = "Tu solicitud de O+ vence en 2 horas",
            subtitulo = "Renuévala o ciérrala desde el panel",
            tiempoRelativo = "hace 30 min",
            leida = false,
        ),
    )
}
