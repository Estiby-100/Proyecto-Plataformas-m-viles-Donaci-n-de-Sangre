package com.uvg.gotavida.ui.vistaadmin.solicitud.nueva_solicitud

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NuevaSolicitudViewModel : ViewModel() {

    var state by mutableStateOf(
        NuevaSolicitudState()
    )
        private set

    fun onEvent(event: NuevaSolicitudEvent) {

        when (event) {

            is NuevaSolicitudEvent.NombreChanged -> {
                state = state.copy(
                    nombreCompleto = event.nombre
                )
            }

            is NuevaSolicitudEvent.TipoSangreChanged -> {
                state = state.copy(
                    tipoSangre = event.tipo
                )
            }

            is NuevaSolicitudEvent.SignoChanged -> {
                state = state.copy(
                    signo = event.signo
                )
            }

            is NuevaSolicitudEvent.DepartamentoChanged -> {
                state = state.copy(
                    departamento = event.departamento
                )
            }

            is NuevaSolicitudEvent.DetallesChanged -> {
                state = state.copy(
                    detalles = event.detalles
                )
            }

            NuevaSolicitudEvent.EnviarSolicitud -> {
                enviarSolicitud()
            }

            NuevaSolicitudEvent.BackClick -> {
                volverAtras()
            }
        }
    }

    private fun enviarSolicitud() {
        // Implementar en futuras fases
    }

    private fun volverAtras() {
        // Implementar en futuras fases
    }
}