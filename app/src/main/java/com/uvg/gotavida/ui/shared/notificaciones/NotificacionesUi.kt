package com.uvg.gotavida.ui.shared.notificaciones

import com.uvg.gotavida.data.model.Notificacion
import com.uvg.gotavida.data.model.RolCuenta

data class NotificacionesState(
    val cargando: Boolean = true,
    val rol: RolCuenta = RolCuenta.DONANTE,
    val notificaciones: List<Notificacion> = emptyList(),
)

sealed interface NotificacionesEvent {
    /** Carga las notificaciones del rol que llega por navegación. */
    data class Cargar(
        val rol: RolCuenta
    ) : NotificacionesEvent
    data object Marcar : NotificacionesEvent
    data class Abrir(
        val notificacionId: String
    ) : NotificacionesEvent
    data object BackClick : NotificacionesEvent
}
