package com.uvg.gotavida.ui.donante.detalle

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class DetalleSolicitudViewModel : ViewModel() {

    var state by mutableStateOf(
        DetalleSolicitudState()
    )
        private set

    fun cargarSolicitud(solicitudId: String) {

        state = state.copy(
            solicitud =
                FakeSolicitudesDataSource
                    .obtenerSolicitudPorId(solicitudId)
                    ?: FakeSolicitudesDataSource
                        .obtenerSolicitudesUrgentesCompatibles()
                        .first()
        )
    }

    fun onEvent(event: DetalleSolicitudEvent) {

        when (event) {

            DetalleSolicitudEvent.DialogoRechazo -> {
                state = state.copy(
                    dialogoRechazo = true
                )
            }

            DetalleSolicitudEvent.Ocultar -> {
                state = state.copy(
                    dialogoRechazo = false
                )
            }

            DetalleSolicitudEvent.Confirmar -> {
                state = state.copy(
                    dialogoRechazo = false
                )
            }

            DetalleSolicitudEvent.Donar -> Unit

            DetalleSolicitudEvent.Mapa -> Unit

            DetalleSolicitudEvent.Atras -> Unit
        }
    }
}