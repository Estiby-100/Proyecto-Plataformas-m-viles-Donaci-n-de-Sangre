package com.uvg.gotavida.ui.vistaadmin.solicitud.solicitudes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uvg.gotavida.vistaadmin.solicitud.solicitudes.SolicitudCreada

class SolicitudesViewModel : ViewModel() {

    var state by mutableStateOf(
        SolicitudesState()
    )
        private set

    init {
        onEvent(
            SolicitudesCreadasEvent.CargarSolicitudes
        )
    }

    fun onEvent(
        event: SolicitudesCreadasEvent
    ) {

        when (event) {

            SolicitudesCreadasEvent.CargarSolicitudes -> {

                state = state.copy(
                    cargando = false,
                    solicitudes = emptyList()
                )
            }

            is SolicitudesCreadasEvent.CerrarSolicitud -> {
                // futura implementación
            }

            is SolicitudesCreadasEvent.AsignarSolicitud -> {
                // futura implementación
            }

            SolicitudesCreadasEvent.BackClick -> {
                // futura implementación
            }
        }
    }
}