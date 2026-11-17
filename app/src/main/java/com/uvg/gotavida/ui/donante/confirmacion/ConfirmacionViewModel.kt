package com.uvg.gotavida.ui.donante.confirmacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.launch
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConfirmacionViewModel : ViewModel() {

    private val _state = MutableStateFlow(ConfirmacionUiState())
    val state: StateFlow<ConfirmacionUiState> = _state.asStateFlow()

    fun onEvent(event: ConfirmacionEvent) {

        when (event) {

            is ConfirmacionEvent.Cargar -> {
                cargarSolicitud(event.solicitudId)
            }

            ConfirmacionEvent.MostrarContacto -> {
                _state.update { it.copy(mostrarDialogoContacto = true) }
            }

            ConfirmacionEvent.OcultarContacto -> {
                _state.update { it.copy(mostrarDialogoContacto = false) }
            }

            ConfirmacionEvent.Mostrar -> {
                _state.update { it.copy(mostrarDialogoCancelar = true) }
            }

            ConfirmacionEvent.Ocultar -> {
                _state.update { it.copy(mostrarDialogoCancelar = false) }
            }

            ConfirmacionEvent.Confirmar -> {
                notificarAMedica()
                _state.update { it.copy(mostrarDialogoCancelar = false) }
            }

            // Solo navegan; no cambian el estado.
            ConfirmacionEvent.Mapa -> Unit
            ConfirmacionEvent.Llamar -> Unit
            ConfirmacionEvent.Entendido -> Unit
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

    private fun notificarAMedica() {
        viewModelScope.launch {
            val sol = _state.value.solicitud ?: return@launch
            val repo = runCatching { ServiceLocator.notificacionRepository }.getOrNull()
            val titulo = "Un donante confirmó disponibilidad"
            val subtitulo = "${sol.institucion} · Sangre ${sol.tipoSangre}"
            repo?.agregar(
                rol = com.uvg.gotavida.data.model.RolCuenta.MEDICA,
                tipo = com.uvg.gotavida.data.model.NotificacionTipo.CONFIRMACION,
                titulo = titulo,
                subtitulo = subtitulo
            )
        }
    }
}
