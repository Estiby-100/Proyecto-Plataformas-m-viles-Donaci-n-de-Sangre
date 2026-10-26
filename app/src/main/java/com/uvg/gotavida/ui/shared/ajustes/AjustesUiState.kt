package com.uvg.gotavida.ui.shared.ajustes

import com.uvg.gotavida.data.model.RolCuenta

data class AjustesUiState(
    val rol: RolCuenta = RolCuenta.DONANTE,
    val notificacionesPushEnabled: Boolean = true,
    val alertasCorreoEnabled: Boolean = false,
    val showLogoutDialog: Boolean = false,
)

sealed interface AjustesEvent {
    data class RolAsignado(val rol: RolCuenta) : AjustesEvent
    data object Editar : AjustesEvent
    data object CambiarContrasena : AjustesEvent
    data object Correo : AjustesEvent
    data object Verificacion : AjustesEvent
    data object TogglePush : AjustesEvent
    data object ToggleCorreo : AjustesEvent
    data object Privacidad : AjustesEvent
    data object Ayuda : AjustesEvent
    data object ReportarP : AjustesEvent
    data object MostrarDialogo : AjustesEvent
    data object Confirmar : AjustesEvent
    data object OcultarDialogo : AjustesEvent
    data object BackClick : AjustesEvent
}