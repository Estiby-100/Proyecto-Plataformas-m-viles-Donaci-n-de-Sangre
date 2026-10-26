package com.uvg.gotavida.ui.donante.confirmacion

import com.uvg.gotavida.data.model.SolicitudDonacion

data class ConfirmacionUiState(

    /** Se carga con el solicitudId que llega por navegación; null mientras carga. */
    val solicitud: SolicitudDonacion? = null,
    val mostrarDialogoContacto: Boolean = false,
    val mostrarDialogoCancelar: Boolean = false
)

sealed interface ConfirmacionEvent {

    data class Cargar(val solicitudId: String) : ConfirmacionEvent
    data object MostrarContacto : ConfirmacionEvent
    data object OcultarContacto : ConfirmacionEvent
    data object Mostrar : ConfirmacionEvent
    data object Ocultar : ConfirmacionEvent
    data object Confirmar : ConfirmacionEvent
    data object Mapa : ConfirmacionEvent
    data object Llamar : ConfirmacionEvent
    data object Entendido : ConfirmacionEvent
}
