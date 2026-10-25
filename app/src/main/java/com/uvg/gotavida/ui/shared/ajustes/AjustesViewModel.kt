package com.uvg.gotavida.ui.shared.ajustes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.model.RolCuenta

class AjustesViewModel : ViewModel() {

    var state by mutableStateOf(
        AjustesUiState(
            rol = RolCuenta.DONANTE
        )
    )
        private set

    fun onEvent(
        event: AjustesEvent
    ) {

        when (event) {

            AjustesEvent.TogglePush -> {

                state = state.copy(
                    notificacionesPushEnabled =
                        !state.notificacionesPushEnabled
                )
            }

            AjustesEvent.ToggleCorreo -> {

                state = state.copy(
                    alertasCorreoEnabled =
                        !state.alertasCorreoEnabled
                )
            }

            AjustesEvent.MostrarDialogo -> {

                state = state.copy(
                    showLogoutDialog = true
                )
            }

            AjustesEvent.OcultarDialogo -> {

                state = state.copy(
                    showLogoutDialog = false
                )
            }

            AjustesEvent.Confirmar -> {

                state = state.copy(
                    showLogoutDialog = false
                )
            }

            else -> {
                // Navegacion
            }
        }
    }
}