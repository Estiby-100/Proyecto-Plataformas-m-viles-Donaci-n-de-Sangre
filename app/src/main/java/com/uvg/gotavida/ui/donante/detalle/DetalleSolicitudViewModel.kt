package com.uvg.gotavida.ui.donante.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.launch
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
                cargarSolicitud(event.solicitudId)
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

    /** Primero busca entre los datos de ejemplo (jornadas) y si no, en Firestore. */
    private fun cargarSolicitud(id: String) {
        viewModelScope.launch {
            val solicitud = FakeSolicitudesDataSource.obtenerSolicitudPorId(id)
                ?: runCatching { ServiceLocator.solicitudesRepository.obtenerPorId(id) }.getOrNull()
            _state.update { it.copy(solicitud = solicitud) }
        }
    }
}
