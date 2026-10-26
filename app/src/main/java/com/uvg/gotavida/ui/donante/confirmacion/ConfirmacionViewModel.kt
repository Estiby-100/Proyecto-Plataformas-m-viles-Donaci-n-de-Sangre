package com.uvg.gotavida.ui.donante.confirmacion

import androidx.lifecycle.ViewModel
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
                _state.update {
                    it.copy(
                        solicitud = FakeSolicitudesDataSource
                            .obtenerSolicitudPorId(event.solicitudId)
                    )
                }
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
                _state.update { it.copy(mostrarDialogoCancelar = false) }
            }

            // Solo navegan; no cambian el estado.
            ConfirmacionEvent.Mapa -> Unit
            ConfirmacionEvent.Llamar -> Unit
            ConfirmacionEvent.Entendido -> Unit
        }
    }
}
