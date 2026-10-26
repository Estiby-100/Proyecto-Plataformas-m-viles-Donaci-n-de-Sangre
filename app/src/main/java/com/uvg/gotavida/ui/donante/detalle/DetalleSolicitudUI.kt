package com.uvg.gotavida.ui.donante.detalle

import com.uvg.gotavida.data.model.SolicitudDonacion

data class DetalleSolicitudState(
    val solicitud: SolicitudDonacion? = null,
    val dialogoRechazo: Boolean = false
)

sealed interface DetalleSolicitudEvent {
    data class Cargar(
        val solicitudId: String
    ) : DetalleSolicitudEvent
    data object DialogoRechazo : DetalleSolicitudEvent
    data object Ocultar : DetalleSolicitudEvent
    data object Confirmar : DetalleSolicitudEvent
    data object Donar : DetalleSolicitudEvent
    data object Mapa : DetalleSolicitudEvent
    data object Atras : DetalleSolicitudEvent
}
