package com.uvg.gotavida.ui.shared.ajustes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.uvg.gotavida.data.model.RolCuenta

@Composable
fun AjustesRoute(rol: RolCuenta) {
    var uiState by remember(rol) { mutableStateOf(AjustesUiState(rol = rol)) }

    AjustesScreen(
        uiState = uiState,
        onEditarPerfilClick = { /* TODO: navegar a Editar perfil, aún no diseñada */ },
        onCambiarContrasenaClick = { /* TODO */ },
        onCorreoClick = { /* TODO */ },
        onVerificacionInstitucionalClick = { /* TODO: propuesta pendiente de aprobación */ },
        onTogglePush = { uiState = uiState.copy(notificacionesPushEnabled = !uiState.notificacionesPushEnabled) },
        onToggleCorreo = { uiState = uiState.copy(alertasCorreoEnabled = !uiState.alertasCorreoEnabled) },
        onPrivacidadClick = { /* TODO */ },
        onCentroAyudaClick = { /* TODO */ },
        onReportarProblemaClick = { /* TODO */ },
        onCerrarSesionClick = { uiState = uiState.copy(showLogoutDialog = true) },
        onConfirmarCerrarSesion = { /* TODO: cerrar sesión real y navegar a Splash */ },
        onDismissLogoutDialog = { uiState = uiState.copy(showLogoutDialog = false) },
        onBackClick = { /* TODO: navegar de regreso al Home del rol correspondiente */ },
    )
}
