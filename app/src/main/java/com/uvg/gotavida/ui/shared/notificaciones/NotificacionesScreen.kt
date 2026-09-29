package com.uvg.gotavida.ui.shared.notificaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.fake.FakeNotificacionesDataSource
import com.uvg.gotavida.data.model.Notificacion
import com.uvg.gotavida.data.model.NotificacionTipo
import com.uvg.gotavida.data.model.RolCuenta
import com.uvg.gotavida.ui.theme.GotaVidaTheme

@Composable
fun NotificacionesScreen(
    uiState: NotificacionesUiState,
    onMarkAllReadClick: () -> Unit,
    onNotificationClick: (Notificacion) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
            Text(
                text = "Notificaciones",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "Marcar todas",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable { onMarkAllReadClick() },
            )
        }

        if (uiState.notificaciones.isEmpty()) {
            EmptyState()
        } else {
            LazyColumn {
                items(uiState.notificaciones, key = { it.id }) { notificacion ->
                    NotificacionItem(
                        notificacion = notificacion,
                        onClick = { onNotificationClick(notificacion) },
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificacionItem(notificacion: Notificacion, onClick: () -> Unit) {
    
    val rowBackground = if (!notificacion.leida) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowBackground)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(40.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = iconoParaTipo(notificacion.tipo),
                    contentDescription = null,
                    tint = colorParaTipo(notificacion.tipo),
                    modifier = Modifier.size(20.dp),
                )
            }
        }
        Spacer(Modifier.padding(start = 12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notificacion.titulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = notificacion.subtitulo,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = notificacion.tiempoRelativo,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (!notificacion.leida) {
            Spacer(Modifier.padding(start = 8.dp))
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(8.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
            )
        }
    }
}

@Composable
private fun iconoParaTipo(tipo: NotificacionTipo): ImageVector = when (tipo) {
    NotificacionTipo.SOLICITUD -> Icons.Filled.WaterDrop
    NotificacionTipo.CONFIRMACION -> Icons.Filled.CheckCircle
    NotificacionTipo.RECORDATORIO -> Icons.Filled.WaterDrop
    NotificacionTipo.VERIFICACION -> Icons.Filled.VerifiedUser
    NotificacionTipo.ALERTA -> Icons.Filled.Schedule
}

@Composable
private fun colorParaTipo(tipo: NotificacionTipo) = when (tipo) {
    NotificacionTipo.SOLICITUD -> MaterialTheme.colorScheme.primary
    NotificacionTipo.CONFIRMACION -> MaterialTheme.colorScheme.tertiary
    NotificacionTipo.RECORDATORIO -> MaterialTheme.colorScheme.tertiary
    NotificacionTipo.VERIFICACION -> MaterialTheme.colorScheme.secondary
    NotificacionTipo.ALERTA -> MaterialTheme.colorScheme.error
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.NotificationsNone,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.padding(top = 16.dp))
        Text(
            text = "Todo tranquilo por aquí",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.padding(top = 4.dp))
        Text(
            text = "No tienes notificaciones por ahora.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Notificaciones - Donante")
@Composable
private fun NotificacionesScreenPreviewDonante() {
    GotaVidaTheme {
        NotificacionesScreen(
            uiState = NotificacionesUiState(
                rol = RolCuenta.DONANTE,
                notificaciones = FakeNotificacionesDataSource.donante,
            ),
            onMarkAllReadClick = {}, onNotificationClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Notificaciones - Médica")
@Composable
private fun NotificacionesScreenPreviewMedica() {
    GotaVidaTheme {
        NotificacionesScreen(
            uiState = NotificacionesUiState(
                rol = RolCuenta.MEDICA,
                notificaciones = FakeNotificacionesDataSource.medica,
            ),
            onMarkAllReadClick = {}, onNotificationClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Notificaciones - Vacío")
@Composable
private fun NotificacionesScreenPreviewVacio() {
    GotaVidaTheme {
        NotificacionesScreen(
            uiState = NotificacionesUiState(notificaciones = emptyList()),
            onMarkAllReadClick = {}, onNotificationClick = {}, onBackClick = {},
        )
    }
}
