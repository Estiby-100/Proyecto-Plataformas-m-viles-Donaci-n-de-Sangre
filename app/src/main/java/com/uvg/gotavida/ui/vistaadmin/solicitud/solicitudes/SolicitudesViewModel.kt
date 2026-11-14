package com.uvg.gotavida.ui.vistaadmin.solicitud.solicitudes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.model.SolicitudCreada
import com.uvg.gotavida.data.remote.EstadoSolicitud
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

            SolicitudesCreadasEvent.CargarSolicitudes -> observarSolicitudes()

            // Cerrar o asignar la saca de las solicitudes activas.
            is SolicitudesCreadasEvent.CerrarSolicitud ->
                cambiarEstado(event.solicitud, EstadoSolicitud.CERRADA)

            is SolicitudesCreadasEvent.AsignarSolicitud ->
                cambiarEstado(event.solicitud, EstadoSolicitud.ASIGNADA)

            SolicitudesCreadasEvent.BackClick -> Unit
        }
    }

    /**
     * Escucha en tiempo real las solicitudes activas de esta médica. Los servicios
     * se piden aquí y no en el constructor, y con runCatching, para que el @Preview
     * no necesite Firebase.
     */
    private fun observarSolicitudes() {
        viewModelScope.launch {
            val uid = runCatching { ServiceLocator.authRepository.uidActual }.getOrNull()
            val repositorio = runCatching { ServiceLocator.solicitudesRepository }.getOrNull()
            if (uid == null || repositorio == null) {
                _state.update { it.copy(cargando = false) }
                return@launch
            }
            repositorio.observarDeMedica(uid)
                .catch { _state.update { it.copy(cargando = false) } }
                .collect { lista ->
                    _state.update { it.copy(cargando = false, solicitudes = lista) }
                }
        }
    }

    private fun cambiarEstado(solicitud: SolicitudCreada, estado: EstadoSolicitud) {
        if (solicitud.id.isEmpty()) return
        viewModelScope.launch {
            runCatching {
                ServiceLocator.solicitudesRepository.cambiarEstado(solicitud.id, estado)
            }
        }
    }
}
