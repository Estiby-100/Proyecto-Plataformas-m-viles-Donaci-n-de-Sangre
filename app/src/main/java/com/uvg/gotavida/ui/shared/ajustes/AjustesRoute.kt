package com.uvg.gotavida.ui.shared.ajustes

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.gotavida.data.model.RolCuenta

@Composable
fun AjustesRoute(
    rol: RolCuenta,
    onBackClick: () -> Unit,
    onSesionCerrada: () -> Unit,
    viewModel: AjustesViewModel = viewModel(),
) {
    LaunchedEffect(rol) {
        viewModel.onEvent(AjustesEvent.RolAsignado(rol))
    }

    AjustesScreen(
        viewModel = viewModel,
        // Estas opciones todavía no tienen pantalla propia: muestran un aviso.
        onEditarPerfilClick = { viewModel.onEvent(AjustesEvent.Editar) },
        onCambiarContrasenaClick = { viewModel.onEvent(AjustesEvent.CambiarContrasena) },
        onCorreoClick = { viewModel.onEvent(AjustesEvent.Correo) },
        onVerificacionInstitucionalClick = { viewModel.onEvent(AjustesEvent.Verificacion) },
        onTogglePush = { viewModel.onEvent(AjustesEvent.TogglePush) },
        onToggleCorreo = { viewModel.onEvent(AjustesEvent.ToggleCorreo) },
        onPrivacidadClick = { viewModel.onEvent(AjustesEvent.Privacidad) },
        onCentroAyudaClick = { viewModel.onEvent(AjustesEvent.Ayuda) },
        onReportarProblemaClick = { viewModel.onEvent(AjustesEvent.ReportarP) },
        onCerrarSesionClick = { viewModel.onEvent(AjustesEvent.MostrarDialogo) },
        onConfirmarCerrarSesion = {
            viewModel.onEvent(AjustesEvent.Confirmar)
            onSesionCerrada()
        },
        onDismissLogoutDialog = { viewModel.onEvent(AjustesEvent.OcultarDialogo) },
        onBackClick = {
            viewModel.onEvent(AjustesEvent.BackClick)
            onBackClick()
        },
        onCerrarMensaje = { viewModel.onEvent(AjustesEvent.CerrarMensaje) },
        // La pantalla no usa Scaffold: se separa de la barra de estado y de navegación.
        modifier = Modifier.systemBarsPadding(),
    )
}
