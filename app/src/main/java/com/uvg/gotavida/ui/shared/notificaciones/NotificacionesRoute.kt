package com.uvg.gotavida.ui.shared.notificaciones

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.uvg.gotavida.data.fake.FakeNotificacionesDataSource
import com.uvg.gotavida.data.model.RolCuenta
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * rol se recibirá más adelante desde la sesión real del usuario (quien haya
 * iniciado sesión). Por ahora, quien use este Route decide qué rol simular.
 */
@Composable
fun NotificacionesRoute(
    rol: RolCuenta,
    viewModel: NotificacionesViewModel = viewModel()
) {

    val state = viewModel.state

    NotificacionesScreen(
        uiState = state,
        onMarkAllReadClick = {
            viewModel.onEvent(NotificacionesEvent.Marcar)
        },
        onNotificationClick = { id ->
            viewModel.onEvent(NotificacionesEvent.Abrir(id))
        },
        onBackClick = {
            viewModel.onEvent(NotificacionesEvent.BackClick)
        }
    )
}