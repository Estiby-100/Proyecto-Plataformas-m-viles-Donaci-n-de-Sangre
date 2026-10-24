package com.uvg.gotavida.ui.vistaadmin.solicitud.solicitudes

import com.uvg.gotavida.vistaadmin.solicitud.solicitudes.SolicitudCreada

data class SolicitudesState(
    val cargando: Boolean = true,
    val solicitudes: List<SolicitudCreada> = emptyList()
)

sealed interface SolicitudesCreadasEvent {

    data object BackClick : SolicitudesCreadasEvent

    data class CerrarSolicitud(
        val solicitud: SolicitudCreada
    ) : SolicitudesCreadasEvent

    data class AsignarSolicitud(
        val solicitud: SolicitudCreada
    ) : SolicitudesCreadasEvent

    data object CargarSolicitudes : SolicitudesCreadasEvent
}