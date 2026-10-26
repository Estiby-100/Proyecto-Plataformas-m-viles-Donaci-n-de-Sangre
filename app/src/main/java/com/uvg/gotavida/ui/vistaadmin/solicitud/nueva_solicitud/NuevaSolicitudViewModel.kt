package com.uvg.gotavida.ui.vistaadmin.solicitud.nueva_solicitud

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NuevaSolicitudViewModel : ViewModel() {

    private val _state = MutableStateFlow(NuevaSolicitudState())
    val state: StateFlow<NuevaSolicitudState> = _state.asStateFlow()

    fun onEvent(event: NuevaSolicitudEvent) {

        when (event) {

            is NuevaSolicitudEvent.NombreChanged -> {
                _state.update { it.copy(nombreCompleto = event.nombre) }
            }

            is NuevaSolicitudEvent.TipoSangreChanged -> {
                _state.update { it.copy(tipoSangre = event.tipo) }
            }

            is NuevaSolicitudEvent.SignoChanged -> {
                _state.update { it.copy(signo = event.signo) }
            }

            is NuevaSolicitudEvent.DepartamentoChanged -> {
                _state.update { it.copy(departamento = event.departamento) }
            }

            is NuevaSolicitudEvent.DetallesChanged -> {
                _state.update { it.copy(detalles = event.detalles) }
            }

            // Con datos falsos, enviar solo navega a la lista de solicitudes.
            NuevaSolicitudEvent.EnviarSolicitud -> Unit

            NuevaSolicitudEvent.BackClick -> Unit
        }
    }
}
