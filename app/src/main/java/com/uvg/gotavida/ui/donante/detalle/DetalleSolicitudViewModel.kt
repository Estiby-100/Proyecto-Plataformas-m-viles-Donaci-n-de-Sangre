package com.uvg.gotavida.ui.donante.detalle

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetalleSolicitudViewModel : ViewModel() {

    private val _state = MutableStateFlow(DetalleSolicitudState())
    val state: StateFlow<DetalleSolicitudState> = _state.asStateFlow()

    fun onEvent(event: DetalleSolicitudEvent) {

        when (event) {

            is DetalleSolicitudEvent.Cargar -> {
                _state.update {
                    it.copy(
                        solicitud = FakeSolicitudesDataSource
                            .obtenerSolicitudPorId(event.solicitudId)
                    )
                }
            }

            DetalleSolicitudEvent.DialogoRechazo -> {
                _state.update { it.copy(dialogoRechazo = true) }
            }

            DetalleSolicitudEvent.Ocultar -> {
                _state.update { it.copy(dialogoRechazo = false) }
            }

            DetalleSolicitudEvent.Confirmar -> {
                _state.update { it.copy(dialogoRechazo = false) }
            }

            // Solo navegan; no cambian el estado.
            DetalleSolicitudEvent.Donar -> Unit
            DetalleSolicitudEvent.Mapa -> Unit
            DetalleSolicitudEvent.Atras -> Unit
        }
    }
}
