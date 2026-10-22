package com.uvg.gotavida.ui.vistaadmin.solicitud.cerrada_asignada

data class CerradaAsignadaState(
    val isLoading: Boolean = false
)

sealed interface CerradaAsignadaEvent {

    data object BackClick : CerradaAsignadaEvent
}