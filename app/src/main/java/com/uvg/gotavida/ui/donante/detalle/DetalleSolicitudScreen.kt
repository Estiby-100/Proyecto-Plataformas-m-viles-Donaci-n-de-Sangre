package com.uvg.gotavida.ui.donante.detalle

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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import com.uvg.gotavida.data.model.NivelUrgencia
import com.uvg.gotavida.data.model.SolicitudDonacion
import com.uvg.gotavida.ui.donante.common.BadgeVerificado
import com.uvg.gotavida.ui.donante.common.ChipUrgencia
import com.uvg.gotavida.ui.theme.GotaVidaTheme

@Composable
fun DetalleSolicitudRoute(
    solicitudId: String,
    modifier: Modifier = Modifier
) {
    var mostrarDialogoRechazo by remember { mutableStateOf(false) }
    val solicitud = FakeSolicitudesDataSource.obtenerSolicitudPorId(solicitudId)
        ?: FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles().first()

    DetalleSolicitudScreen(
        solicitud = solicitud,
        mostrarDialogoRechazo = mostrarDialogoRechazo,
        onPuedoDonarClick = {},
        onNoPuedoDonarClick = { mostrarDialogoRechazo = true },
        onConfirmarRechazo = { mostrarDialogoRechazo = false },
        onVolverDialogoRechazo = { mostrarDialogoRechazo = false },
        onVerEnMapaClick = {},
        onAtrasClick = {},
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleSolicitudScreen(
    solicitud: SolicitudDonacion?,
    mostrarDialogoRechazo: Boolean,
    onPuedoDonarClick: () -> Unit,
    onNoPuedoDonarClick: () -> Unit,
    onConfirmarRechazo: () -> Unit,
    onVolverDialogoRechazo: () -> Unit,
    onVerEnMapaClick: () -> Unit,
    onAtrasClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Detalle de solicitud") },
                navigationIcon = {
                    IconButton(onClick = onAtrasClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (solicitud == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            return@Scaffold
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(solicitud.institucion, style = MaterialTheme.typography.titleLarge)
                        Text(
                            solicitud.zona,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (solicitud.institucionVerificada) {
                        BadgeVerificado()
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                ChipUrgencia(urgencia = solicitud.urgencia)
                Spacer(modifier = Modifier.height(8.dp))
                Text(solicitud.tipoSangre, style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(20.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        FilaConIcono(
                            icono = Icons.Filled.LocationOn,
                            texto = "A ${solicitud.distanciaKm} km de ti"
                        )
                        TextButton(onClick = onVerEnMapaClick) {
                            Text("Ver en mapa")
                        }
                        FilaConIcono(
                            icono = Icons.Filled.Schedule,
                            texto = solicitud.vigenciaTexto
                        )
                    }
                }

                if (solicitud.estaCerrada) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Esta solicitud ya no está disponible",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("Antes de continuar", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                TextoConIcono(Icons.Filled.Info, "Completa el cuestionario de orientación antes de confirmar.")
                TextoConIcono(Icons.Filled.Info, "La elegibilidad final será evaluada por el personal médico.")
                TextoConIcono(Icons.Filled.Info, "Consulta con la institución si tienes dudas antes de asistir.")

                Spacer(modifier = Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Filled.VerifiedUser,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Esta solicitud fue publicada por una cuenta institucional verificada.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Surface(shadowElevation = 4.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = onPuedoDonarClick,
                        enabled = !solicitud.estaCerrada,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (solicitud.estaCerrada) "Solicitud cerrada" else "¿Puedo donar?")
                    }
                    TextButton(onClick = onNoPuedoDonarClick, enabled = !solicitud.estaCerrada) {
                        Text("No puedo donar esta vez")
                    }
                }
            }
        }

        if (mostrarDialogoRechazo) {
            AlertDialog(
                onDismissRequest = onVolverDialogoRechazo,
                title = { Text("¿No puedes donar esta vez?") },
                text = { Text("Informaremos a la institución que no estás disponible para esta solicitud.") },
                confirmButton = {
                    TextButton(onClick = onConfirmarRechazo) { Text("Confirmar") }
                },
                dismissButton = {
                    TextButton(onClick = onVolverDialogoRechazo) { Text("Volver") }
                }
            )
        }
    }
}

@Composable
private fun FilaConIcono(icono: androidx.compose.ui.graphics.vector.ImageVector, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icono, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(texto, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun TextoConIcono(icono: androidx.compose.ui.graphics.vector.ImageVector, texto: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(texto, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewDetalleNormal() {
    GotaVidaTheme {
        DetalleSolicitudScreen(
            solicitud = FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles().first(),
            mostrarDialogoRechazo = false,
            onPuedoDonarClick = {},
            onNoPuedoDonarClick = {},
            onConfirmarRechazo = {},
            onVolverDialogoRechazo = {},
            onVerEnMapaClick = {},
            onAtrasClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewDetalleConDialogoRechazo() {
    GotaVidaTheme {
        DetalleSolicitudScreen(
            solicitud = FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles().first(),
            mostrarDialogoRechazo = true,
            onPuedoDonarClick = {},
            onNoPuedoDonarClick = {},
            onConfirmarRechazo = {},
            onVolverDialogoRechazo = {},
            onVerEnMapaClick = {},
            onAtrasClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewDetalleCerrada() {
    GotaVidaTheme {
        DetalleSolicitudScreen(
            solicitud = FakeSolicitudesDataSource.obtenerSolicitudCerradaDeEjemplo(),
            mostrarDialogoRechazo = false,
            onPuedoDonarClick = {},
            onNoPuedoDonarClick = {},
            onConfirmarRechazo = {},
            onVolverDialogoRechazo = {},
            onVerEnMapaClick = {},
            onAtrasClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewDetalleCargando() {
    GotaVidaTheme {
        DetalleSolicitudScreen(
            solicitud = null,
            mostrarDialogoRechazo = false,
            onPuedoDonarClick = {},
            onNoPuedoDonarClick = {},
            onConfirmarRechazo = {},
            onVolverDialogoRechazo = {},
            onVerEnMapaClick = {},
            onAtrasClick = {}
        )
    }
}
