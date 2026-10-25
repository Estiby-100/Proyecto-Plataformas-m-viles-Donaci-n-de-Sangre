package com.uvg.gotavida.ui.donante.home

import com.uvg.gotavida.data.model.SolicitudDonacion

data class HomeDonanteState(
    val estado: EstadoFeed = EstadoFeed.CARGANDO,
    val disponible: Boolean = true,
    val filtroSeleccionado: FiltroFeed = FiltroFeed.TODAS,
    val urgentes: List<SolicitudDonacion> = emptyList(),
    val jornadas: List<SolicitudDonacion> = emptyList()
)

sealed interface HomeDonanteEvent {
    data class Disponibilidad(
        val disponible: Boolean
    ) : HomeDonanteEvent
    data class Filtro(
        val filtro: FiltroFeed
    ) : HomeDonanteEvent
    data object Cargar : HomeDonanteEvent
    data object Notificaciones : HomeDonanteEvent
    data class Detalle(
        val solicitudId: String
    ) : HomeDonanteEvent
}