package com.uvg.gotavida.ui.vistaadmin.solicitud.solicitudes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.fake.SolicitudesCreadasStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SolicitudesViewModel : ViewModel() {

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
                viewModelScope.launch {
                    SolicitudesCreadasStore.solicitudes.collect { lista ->
                        _state.update { it.copy(cargando = false, solicitudes = lista) }
                    }
                }
            }

            // Cerrar o asignar la saca de las solicitudes activas.
            is SolicitudesCreadasEvent.CerrarSolicitud ->
                SolicitudesCreadasStore.quitar(event.solicitud)

            is SolicitudesCreadasEvent.AsignarSolicitud ->
                SolicitudesCreadasStore.quitar(event.solicitud)

            SolicitudesCreadasEvent.BackClick -> Unit
        }
    }
}
