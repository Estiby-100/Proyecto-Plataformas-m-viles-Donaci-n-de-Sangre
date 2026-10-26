package com.uvg.gotavida.ui.shared.ajustes

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.auth.CuentaRepository
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AjustesViewModel : ViewModel() {

    // Se pide solo al cerrar sesión, así los @Preview de AjustesScreen
    // (que crean este ViewModel) no necesitan Firebase ni Room.
    private val cuentas: CuentaRepository by lazy { ServiceLocator.cuentaRepository }

    private val _state = MutableStateFlow(AjustesUiState())
    val state: StateFlow<AjustesUiState> = _state.asStateFlow()

    fun onEvent(
        event: AjustesEvent
    ) {

        when (event) {

            is AjustesEvent.RolAsignado -> {
                _state.update { it.copy(rol = event.rol) }
            }

            AjustesEvent.TogglePush -> {
                _state.update {
                    it.copy(notificacionesPushEnabled = !it.notificacionesPushEnabled)
                }
            }

            AjustesEvent.ToggleCorreo -> {
                _state.update {
                    it.copy(alertasCorreoEnabled = !it.alertasCorreoEnabled)
                }
            }

            AjustesEvent.MostrarDialogo -> {
                _state.update { it.copy(showLogoutDialog = true) }
            }

            AjustesEvent.OcultarDialogo -> {
                _state.update { it.copy(showLogoutDialog = false) }
            }

            AjustesEvent.Confirmar -> {
                cuentas.cerrarSesion()
                _state.update { it.copy(showLogoutDialog = false) }
            }

            // Opciones sin pantalla todavía o que solo navegan.
            AjustesEvent.Editar,
            AjustesEvent.CambiarContrasena,
            AjustesEvent.Correo,
            AjustesEvent.Verificacion,
            AjustesEvent.Privacidad,
            AjustesEvent.Ayuda,
            AjustesEvent.ReportarP,
            AjustesEvent.BackClick -> Unit
        }
    }
}
