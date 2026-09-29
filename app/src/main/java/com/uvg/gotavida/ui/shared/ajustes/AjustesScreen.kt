package com.uvg.gotavida.ui.shared.ajustes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.model.RolCuenta
import com.uvg.gotavida.ui.theme.GotaVidaTheme

@Composable
fun AjustesScreen(
    uiState: AjustesUiState,
    onEditarPerfilClick: () -> Unit,
    onCambiarContrasenaClick: () -> Unit,
    onCorreoClick: () -> Unit,
    onVerificacionInstitucionalClick: () -> Unit,
    onTogglePush: () -> Unit,
    onToggleCorreo: () -> Unit,
    onPrivacidadClick: () -> Unit,
    onCentroAyudaClick: () -> Unit,
    onReportarProblemaClick: () -> Unit,
    onCerrarSesionClick: () -> Unit,
    onConfirmarCerrarSesion: () -> Unit,
    onDismissLogoutDialog: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
            Text(
                text = "Ajustes",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        SectionTitle("CUENTA")
        SettingsListItem("Editar perfil", onEditarPerfilClick)
        SettingsListItem("Cambiar contraseña", onCambiarContrasenaClick)
        SettingsListItem("Correo electrónico", onCorreoClick)
        if (uiState.rol == RolCuenta.MEDICA) {
            SettingsListItem("Ver estado de verificación institucional", onVerificacionInstitucionalClick)
        }

        SectionTitle("NOTIFICACIONES")
        SwitchRow("Recibir notificaciones push", uiState.notificacionesPushEnabled, onTogglePush)
        SwitchRow("Recibir alertas por correo", uiState.alertasCorreoEnabled, onToggleCorreo)

        SectionTitle("PRIVACIDAD")
        SettingsListItem(
            label = "Ver qué información compartimos con instituciones",
            onClick = onPrivacidadClick,
            leadingIcon = Icons.Filled.Shield,
        )

        SectionTitle("SOPORTE")
        SettingsListItem("Centro de ayuda", onCentroAyudaClick)
        SettingsListItem("Reportar un problema", onReportarProblemaClick)

        Spacer(Modifier.height(32.dp))
        Text(
            text = "Cerrar sesión",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable { onCerrarSesionClick() },
        )

        Spacer(Modifier.height(24.dp))
        Text(
            text = "Versión 1.0.0",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(24.dp))
    }

    if (uiState.showLogoutDialog) {
        AlertDialog(
            onDismissRequest = onDismissLogoutDialog,
            title = { Text("¿Cerrar sesión?") },
            confirmButton = {
                TextButton(onClick = onConfirmarCerrarSesion) {
                    Text("Cerrar sesión", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissLogoutDialog) {
                    Text("Cancelar")
                }
            },
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = 24.dp, bottom = 4.dp),
    )
}

@Composable
private fun SettingsListItem(
    label: String,
    onClick: () -> Unit,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 12.dp),
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SwitchRow(label: String, checked: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Switch(
            checked = checked,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.secondary),
        )
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Ajustes - Donante")
@Composable
private fun AjustesScreenPreviewDonante() {
    GotaVidaTheme {
        AjustesScreen(
            uiState = AjustesUiState(rol = RolCuenta.DONANTE),
            onEditarPerfilClick = {}, onCambiarContrasenaClick = {}, onCorreoClick = {},
            onVerificacionInstitucionalClick = {}, onTogglePush = {}, onToggleCorreo = {},
            onPrivacidadClick = {}, onCentroAyudaClick = {}, onReportarProblemaClick = {},
            onCerrarSesionClick = {}, onConfirmarCerrarSesion = {}, onDismissLogoutDialog = {},
            onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Ajustes - Médica (con ítem extra)")
@Composable
private fun AjustesScreenPreviewMedica() {
    GotaVidaTheme {
        AjustesScreen(
            uiState = AjustesUiState(rol = RolCuenta.MEDICA),
            onEditarPerfilClick = {}, onCambiarContrasenaClick = {}, onCorreoClick = {},
            onVerificacionInstitucionalClick = {}, onTogglePush = {}, onToggleCorreo = {},
            onPrivacidadClick = {}, onCentroAyudaClick = {}, onReportarProblemaClick = {},
            onCerrarSesionClick = {}, onConfirmarCerrarSesion = {}, onDismissLogoutDialog = {},
            onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Ajustes - Diálogo cerrar sesión")
@Composable
private fun AjustesScreenPreviewDialog() {
    GotaVidaTheme {
        AjustesScreen(
            uiState = AjustesUiState(rol = RolCuenta.DONANTE, showLogoutDialog = true),
            onEditarPerfilClick = {}, onCambiarContrasenaClick = {}, onCorreoClick = {},
            onVerificacionInstitucionalClick = {}, onTogglePush = {}, onToggleCorreo = {},
            onPrivacidadClick = {}, onCentroAyudaClick = {}, onReportarProblemaClick = {},
            onCerrarSesionClick = {}, onConfirmarCerrarSesion = {}, onDismissLogoutDialog = {},
            onBackClick = {},
        )
    }
}
