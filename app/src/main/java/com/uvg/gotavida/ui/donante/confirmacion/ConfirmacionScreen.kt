package com.uvg.gotavida.ui.donante.confirmacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import com.uvg.gotavida.data.model.SolicitudDonacion
import com.uvg.gotavida.ui.donante.common.DialogoContactoInstitucion
import com.uvg.gotavida.ui.donante.common.SolicitudCard
import com.uvg.gotavida.ui.theme.GotaVidaTheme

@Composable
fun ConfirmacionRoute(
    modifier: Modifier = Modifier
) {
    val solicitud = remember { FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles().first() }
    var mostrarDialogoContacto by remember { mutableStateOf(false) }
    var mostrarDialogoCancelar by remember { mutableStateOf(false) }

    ConfirmacionScreen(
        solicitud = solicitud,
        mostrarDialogoContacto = mostrarDialogoContacto,
        mostrarDialogoCancelar = mostrarDialogoCancelar,
        onAbrirEnMapaClick = { },
        onContactarInstitucionClick = { mostrarDialogoContacto = true },
        onCerrarDialogoContacto = { mostrarDialogoContacto = false },
        onEntendidoClick = { },
        onCancelarParticipacionClick = { mostrarDialogoCancelar = true },
        onVolverDialogoCancelar = { mostrarDialogoCancelar = false },
        onConfirmarCancelacion = { mostrarDialogoCancelar = false },
        modifier = modifier
    )
}

@Composable
fun ConfirmacionScreen(
    solicitud: SolicitudDonacion,
    mostrarDialogoContacto: Boolean,
    mostrarDialogoCancelar: Boolean,
    onAbrirEnMapaClick: () -> Unit,
    onContactarInstitucionClick: () -> Unit,
    onCerrarDialogoContacto: () -> Unit,
    onEntendidoClick: () -> Unit,
    onCancelarParticipacionClick: () -> Unit,
    onVolverDialogoCancelar: () -> Unit,
    onConfirmarCancelacion: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = paddingValues.calculateTopPadding() + 24.dp,
                    bottom = paddingValues.calculateBottomPadding() + 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = androidx.compose.foundation.shape.CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(40.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "¡Confirmaste tu disponibilidad!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "La institución ya recibió tu confirmación.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }

                item {
                    SolicitudCard(solicitud = solicitud, onAccionClick = null)
                }

                item {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Cómo llegar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = solicitud.direccion,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Horario: ${solicitud.horario}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedButton(
                                onClick = onAbrirEnMapaClick,
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Map,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Abrir en mapa")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = onContactarInstitucionClick,
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Phone,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Contactar institución")
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Revisa en Privacidad qué información compartes con las instituciones.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = onEntendidoClick,
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Entendido")
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        TextButton(
                            onClick = onCancelarParticipacionClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Cancelar mi participación",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            if (mostrarDialogoContacto) {
                DialogoContactoInstitucion(
                    institucion = solicitud.institucion,
                    telefono = solicitud.telefonoInstitucion,
                    onLlamarClick = onCerrarDialogoContacto,
                    onCancelarClick = onCerrarDialogoContacto
                )
            }

            if (mostrarDialogoCancelar) {
                AlertDialog(
                    onDismissRequest = onVolverDialogoCancelar,
                    title = { Text("¿Cancelar tu participación?") },
                    text = { Text("La institución sabrá que ya no estás disponible para esta solicitud.") },
                    confirmButton = {
                        TextButton(onClick = onConfirmarCancelacion) {
                            Text("Confirmar cancelación", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = onVolverDialogoCancelar) { Text("Volver") }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewConfirmacionNormal() {
    GotaVidaTheme {
        ConfirmacionScreen(
            solicitud = FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles().first(),
            mostrarDialogoContacto = false,
            mostrarDialogoCancelar = false,
            onAbrirEnMapaClick = {},
            onContactarInstitucionClick = {},
            onCerrarDialogoContacto = {},
            onEntendidoClick = {},
            onCancelarParticipacionClick = {},
            onVolverDialogoCancelar = {},
            onConfirmarCancelacion = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewConfirmacionConDialogoContacto() {
    GotaVidaTheme {
        ConfirmacionScreen(
            solicitud = FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles().first(),
            mostrarDialogoContacto = true,
            mostrarDialogoCancelar = false,
            onAbrirEnMapaClick = {},
            onContactarInstitucionClick = {},
            onCerrarDialogoContacto = {},
            onEntendidoClick = {},
            onCancelarParticipacionClick = {},
            onVolverDialogoCancelar = {},
            onConfirmarCancelacion = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewConfirmacionConDialogoCancelar() {
    GotaVidaTheme {
        ConfirmacionScreen(
            solicitud = FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles().first(),
            mostrarDialogoContacto = false,
            mostrarDialogoCancelar = true,
            onAbrirEnMapaClick = {},
            onContactarInstitucionClick = {},
            onCerrarDialogoContacto = {},
            onEntendidoClick = {},
            onCancelarParticipacionClick = {},
            onVolverDialogoCancelar = {},
            onConfirmarCancelacion = {}
        )
    }
}
