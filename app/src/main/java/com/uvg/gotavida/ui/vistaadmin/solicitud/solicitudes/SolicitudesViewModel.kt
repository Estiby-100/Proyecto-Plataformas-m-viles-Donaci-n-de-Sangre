package com.uvg.gotavida.ui.vistaadmin.solicitud.solicitudes

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.vistaadmin.solicitud.solicitudes.SolicitudCreada
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SolicitudesViewModel : ViewModel() {

    // Datos falsos de la entrega (antes estaban dentro de SolicitudesScreen).
    private val ejemplos = listOf(

        SolicitudCreada(
            nombre = "Hospital Roosevelt",
            ubicacion = "Guatemala",
            tipoSangre = "O-",
        ),

        SolicitudCreada(
            nombre = "Hospital San Juan de Dios",
            ubicacion = "Guatemala",
            tipoSangre = "A+"
        ),

        SolicitudCreada(
            nombre = "Hospital Regional de Quetzaltenango",
            ubicacion = "Quetzaltenango",
            tipoSangre = "B+"
        ),

        SolicitudCreada(
            nombre = "IGSS Zona 9",
            ubicacion = "Guatemala",
            tipoSangre = "AB-"
        ),

        SolicitudCreada(
            nombre = "Hospital Nacional de Escuintla",
            ubicacion = "Escuintla",
            tipoSangre = "O+"
        ),

        SolicitudCreada(
            nombre = "Hospital de Cobán",
            ubicacion = "Alta Verapaz",
            tipoSangre = "A-"
        )
    )

    private val _state = MutableStateFlow(SolicitudesState())
    val state: StateFlow<SolicitudesState> = _state.asStateFlow()

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
                _state.update {
                    it.copy(cargando = false, solicitudes = ejemplos)
                }
            }

            // Al cerrar o asignar, la solicitud sale de la lista activa.
            is SolicitudesCreadasEvent.CerrarSolicitud -> quitar(event.solicitud)

            is SolicitudesCreadasEvent.AsignarSolicitud -> quitar(event.solicitud)

            SolicitudesCreadasEvent.BackClick -> Unit
        }
    }

    private fun quitar(solicitud: SolicitudCreada) {
        _state.update {
            it.copy(solicitudes = it.solicitudes - solicitud)
        }
    }
}
