package com.uvg.gotavida.ui.donante.confirmacion

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ConfirmacionViewModel : ViewModel() {

    var state by mutableStateOf(
        ConfirmacionUiState(
            solicitud =
                FakeSolicitudesDataSource
                    .obtenerSolicitudesUrgentesCompatibles()
                    .first()
        )
    )
        private set

    fun onEvent(event: ConfirmacionEvent) {

        when (event) {

            ConfirmacionEvent.MostrarContacto -> {

                state = state.copy(
                    mostrarDialogoContacto = true
                )
            }

            ConfirmacionEvent.OcultarContacto -> {

                state = state.copy(
                    mostrarDialogoContacto = false
                )
            }

            ConfirmacionEvent.Mostrar -> {

                state = state.copy(
                    mostrarDialogoCancelar = true
                )
            }

            ConfirmacionEvent.Ocultar -> {

                state = state.copy(
                    mostrarDialogoCancelar = false
                )
            }

            ConfirmacionEvent.Confirmar -> {

                state = state.copy(
                    mostrarDialogoCancelar = false
                )
            }

            else -> Unit
        }
    }
}