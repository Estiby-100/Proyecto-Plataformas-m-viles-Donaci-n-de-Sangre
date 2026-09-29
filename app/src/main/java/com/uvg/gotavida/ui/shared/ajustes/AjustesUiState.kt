package com.uvg.gotavida.ui.shared.ajustes

import com.uvg.gotavida.data.model.RolCuenta

data class AjustesUiState(
    val rol: RolCuenta = RolCuenta.DONANTE,
    val notificacionesPushEnabled: Boolean = true,
    val alertasCorreoEnabled: Boolean = false,
    val showLogoutDialog: Boolean = false,
)
