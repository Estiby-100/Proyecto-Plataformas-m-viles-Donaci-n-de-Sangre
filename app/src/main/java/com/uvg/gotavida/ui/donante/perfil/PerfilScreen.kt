package com.uvg.gotavida.ui.donante.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.fake.FakePerfilDataSource
import com.uvg.gotavida.data.model.EstadoHistorial
import com.uvg.gotavida.data.model.Insignia
import com.uvg.gotavida.data.model.ItemHistorial
import com.uvg.gotavida.ui.theme.GotaVidaTheme
import com.uvg.gotavida.ui.theme.WarningAccessible
import com.uvg.gotavida.ui.theme.WarningContainer

/**
 * Pantalla 11: Perfil del donante.
 *
 * El switch de disponibilidad es conceptualmente el mismo estado que el de
 * Home (pantalla 6); en esta fase de solo vistas cada pantalla mantiene su
 * propio estado local, sin fuente de verdad compartida todavía.
 */
@Composable
fun PerfilRoute(
    modifier: Modifier = Modifier
) {
    var disponible by remember { mutableStateOf(true) }

    PerfilScreen(
        nombre = "Esteban Sánchez",
        tipoSangre = "O+",
        disponible = disponible,
        historial = FakePerfilDataSource.obtenerHistorial(),
        insignias = FakePerfilDataSource.obtenerInsignias(),
        cargando = false,
        onDisponibleChange = { disponible = it },
        modifier = modifier
    )
}

@Composable
fun PerfilScreen(
    nombre: String,
    tipoSangre: String,
    disponible: Boolean,
    historial: List<ItemHistorial>,
    insignias: List<Insignia>,
    cargando: Boolean,
    onDisponibleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->
        if (cargando) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                EncabezadoPerfil(
                    nombre = nombre,
                    tipoSangre = tipoSangre,
                    disponible = disponible,
                    onDisponibleChange = onDisponibleChange
                )
            }

            item {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Próxima fecha para donar",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Aún no podemos estimarla",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Insignias",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    insignias.forEach { insignia ->
                        TarjetaInsignia(insignia = insignia, modifier = Modifier.weight(1f))
                    }
                }
            }

            item {
                Text(
                    text = "Historial",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (historial.isEmpty()) {
                item {
                    Text(
                        text = "Todavía no tienes donaciones registradas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                item {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            historial.forEachIndexed { index, item ->
                                ItemHistorialFila(item = item)
                                if (index < historial.lastIndex) {
                                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                                }
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PrivacyTip,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Privacidad: qué información compartes con las instituciones",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EncabezadoPerfil(
    nombre: String,
    tipoSangre: String,
    disponible: Boolean,
    onDisponibleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = MaterialTheme.shapes.large,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .padding(12.dp)
                            .size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = MaterialTheme.shapes.extraSmall
                    ) {
                        Text(
                            text = tipoSangre,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Disponible para donar",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = if (disponible) "Las instituciones pueden verte en el feed" else "No apareces en nuevas solicitudes",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(checked = disponible, onCheckedChange = onDisponibleChange)
            }
        }
    }
}

@Composable
private fun TarjetaInsignia(
    insignia: Insignia,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            if (insignia.desbloqueada) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(28.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Insignia bloqueada",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = insignia.nombre,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = if (insignia.desbloqueada) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

@Composable
private fun ItemHistorialFila(
    item: ItemHistorial,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.institucion, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = item.fechaTexto,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        val (color, onColor, texto) = when (item.estado) {
            EstadoHistorial.COMPLETADA -> Triple(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.colorScheme.onSecondaryContainer,
                "Completada"
            )
            EstadoHistorial.CANCELADA -> Triple(
                WarningContainer,
                WarningAccessible,
                "Cancelada"
            )
        }
        Surface(color = color, contentColor = onColor, shape = MaterialTheme.shapes.extraSmall) {
            Text(
                text = texto,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewPerfilNormal() {
    GotaVidaTheme {
        PerfilScreen(
            nombre = "Esteban Sánchez",
            tipoSangre = "O+",
            disponible = true,
            historial = FakePerfilDataSource.obtenerHistorial(),
            insignias = FakePerfilDataSource.obtenerInsignias(),
            cargando = false,
            onDisponibleChange = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewPerfilNoDisponible() {
    GotaVidaTheme {
        PerfilScreen(
            nombre = "Esteban Sánchez",
            tipoSangre = "O+",
            disponible = false,
            historial = FakePerfilDataSource.obtenerHistorial(),
            insignias = FakePerfilDataSource.obtenerInsignias(),
            cargando = false,
            onDisponibleChange = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewPerfilHistorialVacio() {
    GotaVidaTheme {
        PerfilScreen(
            nombre = "Esteban Sánchez",
            tipoSangre = "O+",
            disponible = true,
            historial = emptyList(),
            insignias = FakePerfilDataSource.obtenerInsignias(),
            cargando = false,
            onDisponibleChange = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewPerfilCargando() {
    GotaVidaTheme {
        PerfilScreen(
            nombre = "Esteban Sánchez",
            tipoSangre = "O+",
            disponible = true,
            historial = emptyList(),
            insignias = emptyList(),
            cargando = true,
            onDisponibleChange = {}
        )
    }
}
