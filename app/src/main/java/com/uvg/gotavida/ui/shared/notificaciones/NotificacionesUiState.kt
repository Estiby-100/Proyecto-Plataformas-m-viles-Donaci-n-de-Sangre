package com.uvg.gotavida.ui.shared.notificaciones

import com.uvg.gotavida.data.model.Notificacion
import com.uvg.gotavida.data.model.RolCuenta

data class NotificacionesUiState(
    val rol: RolCuenta = RolCuenta.DONANTE,
    val notificaciones: List<Notificacion> = emptyList(),
)
