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

            // Opciones que todavía no tienen pantalla propia.
            AjustesEvent.Editar,
            AjustesEvent.CambiarContrasena,
            AjustesEvent.Correo,
            AjustesEvent.Verificacion,
            AjustesEvent.Privacidad,
            AjustesEvent.Ayuda,
            AjustesEvent.ReportarP -> {
                _state.update { it.copy(mensaje = PROXIMAMENTE) }
            }

            AjustesEvent.CerrarMensaje -> {
                _state.update { it.copy(mensaje = null) }
            }

            AjustesEvent.BackClick -> Unit
        }
    }
}

private const val PROXIMAMENTE = "Esta opción estará disponible en una próxima versión."
