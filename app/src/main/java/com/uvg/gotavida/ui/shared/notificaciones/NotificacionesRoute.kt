package com.uvg.gotavida.ui.shared.notificaciones

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.gotavida.data.model.RolCuenta

/**
 * El rol llega como argumento de navegación (desde el home de cada rol).
 * Tocar una notificación solo la marca como leída: el modelo Notificacion
 * no trae el id de la solicitud, así que todavía no hay a dónde llevarla.
 */
@Composable
fun NotificacionesRoute(
    rol: RolCuenta,
    onBackClick: () -> Unit,
    viewModel: NotificacionesViewModel = viewModel()
) {

    LaunchedEffect(rol) {
        viewModel.onEvent(NotificacionesEvent.Cargar(rol))
    }

    val state by viewModel.state.collectAsState()

    NotificacionesScreen(
        modifier = Modifier.systemBarsPadding(),
        uiState = state,
        onMarkAllReadClick = {
            viewModel.onEvent(NotificacionesEvent.Marcar)
        },
        onNotificationClick = { id ->
            viewModel.onEvent(NotificacionesEvent.Abrir(id))
        },
        onBackClick = {
            viewModel.onEvent(NotificacionesEvent.BackClick)
            onBackClick()
        }
    )
}
