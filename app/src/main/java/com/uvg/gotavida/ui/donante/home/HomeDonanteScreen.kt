package com.uvg.gotavida.ui.donante.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import com.uvg.gotavida.data.model.SolicitudDonacion
import com.uvg.gotavida.ui.donante.common.DonanteBottomNavigation
import com.uvg.gotavida.ui.donante.common.DonanteTab
import com.uvg.gotavida.ui.donante.common.SolicitudCard
import com.uvg.gotavida.ui.theme.GotaVidaTheme

enum class FiltroFeed {
    TODAS,
    SOLICITUDES,
    JORNADAS
}

enum class EstadoFeed {
    CARGANDO,
    VACIO,
    ERROR,
    CON_CONTENIDO
}

@Composable
fun HomeDonanteRoute(
    modifier: Modifier = Modifier
) {
    var disponible by remember { mutableStateOf(true) }
    var filtroSeleccionado by remember { mutableStateOf(FiltroFeed.TODAS) }

    val todasLasSolicitudes = FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles()
    val todasLasJornadas = FakeSolicitudesDataSource.obtenerJornadasCercanas()

    val solicitudesAMostrar = when (filtroSeleccionado) {
        FiltroFeed.JORNADAS -> emptyList()
        else -> todasLasSolicitudes
    }
    val jornadasAMostrar = when (filtroSeleccionado) {
        FiltroFeed.SOLICITUDES -> emptyList()
        else -> todasLasJornadas
    }

    val estado = if (solicitudesAMostrar.isEmpty() && jornadasAMostrar.isEmpty()) {
        EstadoFeed.VACIO
    } else {
        EstadoFeed.CON_CONTENIDO
    }

    HomeDonanteScreen(
        estado = estado,
        disponible = disponible,
        filtroSeleccionado = filtroSeleccionado,
        solicitudesUrgentes = solicitudesAMostrar,
        jornadas = jornadasAMostrar,
        onDisponibleChange = { disponible = it },
        onFiltroSeleccionado = { filtroSeleccionado = it },
        onVerDetalle = {},
        onNotificacionesClick = {},
        tabSeleccionado = DonanteTab.HOME,
        onTabSeleccionado = {},
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDonanteScreen(
    estado: EstadoFeed,
    disponible: Boolean,
    filtroSeleccionado: FiltroFeed,
    solicitudesUrgentes: List<SolicitudDonacion>,
    jornadas: List<SolicitudDonacion>,
    onDisponibleChange: (Boolean) -> Unit,
    onFiltroSeleccionado: (FiltroFeed) -> Unit,
    onVerDetalle: (String) -> Unit,
    onNotificacionesClick: () -> Unit,
    tabSeleccionado: DonanteTab,
    onTabSeleccionado: (DonanteTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Hola, Esteban") },
                actions = {
                    IconButton(onClick = onNotificacionesClick) {
                        BadgedBox(badge = { Badge() }) {
                            Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones")
                        }
                    }
                }
            )
        },
        bottomBar = {
            DonanteBottomNavigation(
                tabSeleccionado = tabSeleccionado,
                onTabSeleccionado = onTabSeleccionado
            )
        }
    ) { innerPadding ->
        when (estado) {
            EstadoFeed.CARGANDO -> HomeCargandoState(innerPadding)
            EstadoFeed.ERROR -> HomeErrorState(innerPadding)
            EstadoFeed.VACIO -> HomeVacioState(
                innerPadding = innerPadding,
                disponible = disponible,
                filtroSeleccionado = filtroSeleccionado,
                onDisponibleChange = onDisponibleChange,
                onFiltroSeleccionado = onFiltroSeleccionado
            )
            EstadoFeed.CON_CONTENIDO -> HomeConContenidoState(
                innerPadding = innerPadding,
                disponible = disponible,
                filtroSeleccionado = filtroSeleccionado,
                solicitudesUrgentes = solicitudesUrgentes,
                jornadas = jornadas,
                onDisponibleChange = onDisponibleChange,
                onFiltroSeleccionado = onFiltroSeleccionado,
                onVerDetalle = onVerDetalle
            )
        }
    }
}

@Composable
private fun TarjetaDisponibilidad(
    disponible: Boolean,
    onDisponibleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (disponible) "Disponible para donar" else "No disponible ahora",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "O+ · Ciudad de Guatemala",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = disponible,
                onCheckedChange = onDisponibleChange,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilaFiltros(
    filtroSeleccionado: FiltroFeed,
    onFiltroSeleccionado: (FiltroFeed) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = filtroSeleccionado == FiltroFeed.TODAS,
            onClick = { onFiltroSeleccionado(FiltroFeed.TODAS) },
            label = { Text("Todas") }
        )
        FilterChip(
            selected = filtroSeleccionado == FiltroFeed.SOLICITUDES,
            onClick = { onFiltroSeleccionado(FiltroFeed.SOLICITUDES) },
            label = { Text("Solicitudes") }
        )
        FilterChip(
            selected = filtroSeleccionado == FiltroFeed.JORNADAS,
            onClick = { onFiltroSeleccionado(FiltroFeed.JORNADAS) },
            label = { Text("Jornadas") }
        )
    }
}

@Composable
private fun HomeConContenidoState(
    innerPadding: PaddingValues,
    disponible: Boolean,
    filtroSeleccionado: FiltroFeed,
    solicitudesUrgentes: List<SolicitudDonacion>,
    jornadas: List<SolicitudDonacion>,
    onDisponibleChange: (Boolean) -> Unit,
    onFiltroSeleccionado: (FiltroFeed) -> Unit,
    onVerDetalle: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = innerPadding.calculateTopPadding() + 12.dp,
            bottom = innerPadding.calculateBottomPadding() + 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TarjetaDisponibilidad(
                disponible = disponible,
                onDisponibleChange = onDisponibleChange
            )
        }
        item {
            FilaFiltros(
                filtroSeleccionado = filtroSeleccionado,
                onFiltroSeleccionado = onFiltroSeleccionado
            )
        }
        if (solicitudesUrgentes.isNotEmpty()) {
            item {
                Text(
                    text = "Urgentes cerca de ti",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            items(solicitudesUrgentes) { solicitud ->
                SolicitudCard(
                    solicitud = solicitud,
                    onAccionClick = { onVerDetalle(solicitud.id) }
                )
            }
        }
        if (jornadas.isNotEmpty()) {
            item {
                Text(
                    text = "Más cerca de ti",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            items(jornadas) { jornada ->
                SolicitudCard(
                    solicitud = jornada,
                    onAccionClick = { onVerDetalle(jornada.id) }
                )
            }
        }
    }
}

@Composable
private fun HomeVacioState(
    innerPadding: PaddingValues,
    disponible: Boolean,
    filtroSeleccionado: FiltroFeed,
    onDisponibleChange: (Boolean) -> Unit,
    onFiltroSeleccionado: (FiltroFeed) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        TarjetaDisponibilidad(disponible = disponible, onDisponibleChange = onDisponibleChange)
        FilaFiltros(filtroSeleccionado = filtroSeleccionado, onFiltroSeleccionado = onFiltroSeleccionado)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No hay solicitudes cerca de ti por ahora",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Te avisaremos apenas haya una compatible",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
            OutlinedButton(
                onClick = { onFiltroSeleccionado(FiltroFeed.TODAS) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Ver todas las solicitudes")
            }
        }
    }
}

@Composable
private fun HomeCargandoState(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun HomeErrorState(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No pudimos cargar el feed",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = {},
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Reintentar")
        }
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewHomeConContenido() {
    GotaVidaTheme {
        HomeDonanteScreen(
            estado = EstadoFeed.CON_CONTENIDO,
            disponible = true,
            filtroSeleccionado = FiltroFeed.TODAS,
            solicitudesUrgentes = FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles(),
            jornadas = FakeSolicitudesDataSource.obtenerJornadasCercanas(),
            onDisponibleChange = {},
            onFiltroSeleccionado = {},
            onVerDetalle = {},
            onNotificacionesClick = {},
            tabSeleccionado = DonanteTab.HOME,
            onTabSeleccionado = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewHomeNoDisponible() {
    GotaVidaTheme {
        HomeDonanteScreen(
            estado = EstadoFeed.CON_CONTENIDO,
            disponible = false,
            filtroSeleccionado = FiltroFeed.TODAS,
            solicitudesUrgentes = FakeSolicitudesDataSource.obtenerSolicitudesUrgentesCompatibles(),
            jornadas = FakeSolicitudesDataSource.obtenerJornadasCercanas(),
            onDisponibleChange = {},
            onFiltroSeleccionado = {},
            onVerDetalle = {},
            onNotificacionesClick = {},
            tabSeleccionado = DonanteTab.HOME,
            onTabSeleccionado = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewHomeCargando() {
    GotaVidaTheme {
        HomeDonanteScreen(
            estado = EstadoFeed.CARGANDO,
            disponible = true,
            filtroSeleccionado = FiltroFeed.TODAS,
            solicitudesUrgentes = emptyList(),
            jornadas = emptyList(),
            onDisponibleChange = {},
            onFiltroSeleccionado = {},
            onVerDetalle = {},
            onNotificacionesClick = {},
            tabSeleccionado = DonanteTab.HOME,
            onTabSeleccionado = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewHomeVacio() {
    GotaVidaTheme {
        HomeDonanteScreen(
            estado = EstadoFeed.VACIO,
            disponible = true,
            filtroSeleccionado = FiltroFeed.TODAS,
            solicitudesUrgentes = emptyList(),
            jornadas = emptyList(),
            onDisponibleChange = {},
            onFiltroSeleccionado = {},
            onVerDetalle = {},
            onNotificacionesClick = {},
            tabSeleccionado = DonanteTab.HOME,
            onTabSeleccionado = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewHomeError() {
    GotaVidaTheme {
        HomeDonanteScreen(
            estado = EstadoFeed.ERROR,
            disponible = true,
            filtroSeleccionado = FiltroFeed.TODAS,
            solicitudesUrgentes = emptyList(),
            jornadas = emptyList(),
            onDisponibleChange = {},
            onFiltroSeleccionado = {},
            onVerDetalle = {},
            onNotificacionesClick = {},
            tabSeleccionado = DonanteTab.HOME,
            onTabSeleccionado = {}
        )
    }
}
