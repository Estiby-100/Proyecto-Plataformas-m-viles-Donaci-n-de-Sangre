package com.uvg.gotavida.vistaadmin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.gotavida.ui.theme.CoralAction
import com.uvg.gotavida.ui.theme.GotaVidaTheme
import com.uvg.gotavida.ui.theme.SuccessAccessible

data class SolicitudCreada(
    val nombre: String,
    val ubicacion: String,
    val tipoSangre: String,
    val dadoresNecesarios: Int
)

@Composable
fun SolicitudesCreadasScreen(
    onBackClick: () -> Unit = {},
    onCerrarSolicitud: (SolicitudCreada) -> Unit = {},
    onAsignarSolicitud: (SolicitudCreada) -> Unit = {}
) {
    val solicitudes: List<SolicitudCreada> = emptyList()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            SolicitudesTopBar(
                onBackClick = onBackClick
            )
        },
    ) { padding ->

        if (solicitudes.isEmpty()) {

            EmptyState(
                modifier = Modifier.padding(padding)
            )

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                solicitudes.forEach { solicitud ->

                    SolicitudCard(
                        solicitud = solicitud,
                        onCerrarSolicitud = {
                            onCerrarSolicitud(solicitud)
                        },
                        onAsignarSolicitud = {
                            onAsignarSolicitud(solicitud)
                        }
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SolicitudesTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Solicitudes Creadas",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            Text(
                text = "Activas",
                color = SuccessAccessible,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Filled.Inbox,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "No hay solicitudes creadas",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Las solicitudes que crees aparecerán aquí.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SolicitudCard(
    solicitud: SolicitudCreada,
    onCerrarSolicitud: () -> Unit,
    onAsignarSolicitud: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(CoralAction),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = solicitud.tipoSangre,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column {

                    Text(
                        text = solicitud.nombre,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = SuccessAccessible,
                            modifier = Modifier.size(12.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(4.dp)
                        )

                        Text(
                            text = solicitud.ubicacion,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Tipo: ${solicitud.tipoSangre} • Necesita ${solicitud.dadoresNecesarios} dadores",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedButton(
                    onClick = onCerrarSolicitud,
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Cerrar solicitud",
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Button(
                    onClick = onAsignarSolicitud,
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Asignar solicitud",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SolicitudesCreadasScreenPreview() {
    GotaVidaTheme {
        SolicitudesCreadasScreen()
    }
}