package com.uvg.gotavida.ui.shared.notificaciones

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.uvg.gotavida.data.fake.FakeNotificacionesDataSource
import com.uvg.gotavida.data.model.RolCuenta

/**
 * rol se recibirá más adelante desde la sesión real del usuario (quien haya
 * iniciado sesión). Por ahora, quien use este Route decide qué rol simular.
 */
@Composable
fun NotificacionesRoute(rol: RolCuenta) {
    var uiState by remember(rol) {
        val notificacionesIniciales = when (rol) {
            RolCuenta.DONANTE -> FakeNotificacionesDataSource.donante
            RolCuenta.MEDICA -> FakeNotificacionesDataSource.medica
        }
        mutableStateOf(NotificacionesUiState(rol = rol, notificaciones = notificacionesIniciales))
    }

    NotificacionesScreen(
        uiState = uiState,
        onMarkAllReadClick = {
            uiState = uiState.copy(notificaciones = uiState.notificaciones.map { it.copy(leida = true) })
        },
        onNotificationClick = { /* TODO: navegar al detalle correspondiente según el tipo */ },
        onBackClick = { /* TODO: navegar de regreso al Home del rol correspondiente */ },
    )
}
