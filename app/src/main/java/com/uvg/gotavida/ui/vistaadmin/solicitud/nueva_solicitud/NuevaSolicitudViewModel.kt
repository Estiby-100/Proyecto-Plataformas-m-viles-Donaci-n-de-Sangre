package com.uvg.gotavida.ui.vistaadmin.solicitud.nueva_solicitud

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.SolicitudesCreadasStore
import com.uvg.gotavida.data.model.SolicitudCreada
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
                _state.update { it.copy(nombreCompleto = event.nombre, errorMessage = null) }
            }

            is NuevaSolicitudEvent.TipoSangreChanged -> {
                _state.update { it.copy(tipoSangre = event.tipo, errorMessage = null) }
            }

            is NuevaSolicitudEvent.SignoChanged -> {
                _state.update { it.copy(signo = event.signo, errorMessage = null) }
            }

            is NuevaSolicitudEvent.DepartamentoChanged -> {
                _state.update { it.copy(departamento = event.departamento, errorMessage = null) }
            }

            is NuevaSolicitudEvent.DetallesChanged -> {
                _state.update { it.copy(detalles = event.detalles) }
            }

            NuevaSolicitudEvent.EnviarSolicitud -> enviar()

            NuevaSolicitudEvent.NavegacionAtendida -> {
                _state.update { it.copy(enviada = false) }
            }

            NuevaSolicitudEvent.BackClick -> Unit
        }
    }

    private fun enviar() {
        val actual = _state.value

        val faltaAlgo = actual.nombreCompleto.isBlank() ||
            actual.tipoSangre.isBlank() ||
            actual.signo.isBlank() ||
            actual.departamento.isBlank()

        if (faltaAlgo) {
            _state.update {
                it.copy(errorMessage = "Completa nombre, tipo de sangre, signo y departamento.")
            }
            return
        }

        SolicitudesCreadasStore.agregar(
            SolicitudCreada(
                nombre = actual.nombreCompleto.trim(),
                ubicacion = actual.departamento,
                tipoSangre = actual.tipoSangre + actual.signo
            )
        )
        _state.update { it.copy(enviada = true) }
    }
}
