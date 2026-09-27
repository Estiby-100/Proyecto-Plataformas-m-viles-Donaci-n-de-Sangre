package com.uvg.gotavida.ui.donante.mapa

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List as ListIcon
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
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
import com.uvg.gotavida.data.fake.FakeLugaresDataSource
import com.uvg.gotavida.data.model.LugarMapa
import com.uvg.gotavida.ui.theme.GotaVidaTheme

private enum class VistaMapa { MAPA, LISTA }

@Composable
fun MapaRoute(
    modifier: Modifier = Modifier
) {
    var vista by remember { mutableStateOf(VistaMapa.MAPA) }
    var lugarSeleccionado by remember { mutableStateOf<LugarMapa?>(null) }
    val lugares = remember { FakeLugaresDataSource.obtenerLugaresCercanos() }

    MapaScreen(
        lugares = lugares,
        vistaLista = vista == VistaMapa.LISTA,
        permisoUbicacionConcedido = true,
        lugarSeleccionado = lugarSeleccionado,
        onCambiarVista = { vista = if (vista == VistaMapa.MAPA) VistaMapa.LISTA else VistaMapa.MAPA },
        onLugarClick = { lugarSeleccionado = it },
        onCerrarDetalle = { lugarSeleccionado = null },
        onActivarUbicacionClick = { },
        modifier = modifier
    )
}

@Composable
fun MapaScreen(
    lugares: List<LugarMapa>,
    vistaLista: Boolean,
    permisoUbicacionConcedido: Boolean,
    lugarSeleccionado: LugarMapa?,
    onCambiarVista: () -> Unit,
    onLugarClick: (LugarMapa) -> Unit,
    onCerrarDetalle: () -> Unit,
    onActivarUbicacionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            if (!permisoUbicacionConcedido) {
                VistaSinPermiso(onActivarUbicacionClick = onActivarUbicacionClick)
            } else if (vistaLista) {
                VistaListaLugares(
                    lugares = lugares,
                    onLugarClick = onLugarClick,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                VistaMapaDibujado(
                    lugares = lugares,
                    onLugarClick = onLugarClick,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (permisoUbicacionConcedido) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    BarraBusqueda()
                    Spacer(modifier = Modifier.height(12.dp))
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        SegmentedButton(
                            selected = !vistaLista,
                            onClick = { if (vistaLista) onCambiarVista() },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                        ) {
                            Icon(Icons.Filled.Map, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Mapa")
                        }
                        SegmentedButton(
                            selected = vistaLista,
                            onClick = { if (!vistaLista) onCambiarVista() },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ListIcon,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Lista")
                        }
                    }
                }
            }

            if (lugarSeleccionado != null && !vistaLista) {
                HojaDetalleLugar(
                    lugar = lugarSeleccionado,
                    onCerrarClick = onCerrarDetalle,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun BarraBusqueda(modifier: Modifier = Modifier) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Buscar por zona o institución",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun VistaMapaDibujado(
    lugares: List<LugarMapa>,
    onLugarClick: (LugarMapa) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        lugares.forEach { lugar ->
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = (lugar.xRelativo * 320).dp,
                        top = (lugar.yRelativo * 640).dp
                    )
            ) {
                MarcadorLugar(lugar = lugar, onClick = { onLugarClick(lugar) })
            }
        }

        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Surface(
                shape = CircleShape,
                shadowElevation = 2.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Icon(
                    imageVector = Icons.Filled.MyLocation,
                    contentDescription = "Mi ubicación",
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
private fun MarcadorLugar(
    lugar: LugarMapa,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (lugar.tieneSolicitudesActivas) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }
    Surface(
        color = color,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape,
        shadowElevation = 3.dp,
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = lugar.nombre,
            modifier = Modifier.padding(6.dp)
        )
    }
}

@Composable
private fun VistaListaLugares(
    lugares: List<LugarMapa>,
    onLugarClick: (LugarMapa) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 96.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(lugares) { lugar ->
            TarjetaLugarLista(lugar = lugar, onClick = { onLugarClick(lugar) })
        }
    }
}

@Composable
private fun TarjetaLugarLista(
    lugar: LugarMapa,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = MaterialTheme.shapes.large,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = lugar.nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${lugar.distanciaKm} km · ${lugar.horarioTexto}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (lugar.tieneSolicitudesActivas) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tiene solicitudes activas",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun HojaDetalleLugar(
    lugar: LugarMapa,
    onCerrarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.large,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = lugar.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onCerrarClick) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Cerrar")
                }
            }
            Text(
                text = lugar.direccion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = lugar.horarioTexto,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { }, shape = MaterialTheme.shapes.medium) {
                    Text("Cómo llegar")
                }
                if (lugar.tieneSolicitudesActivas) {
                    Button(onClick = { }, shape = MaterialTheme.shapes.medium) {
                        Text("Ver solicitudes de este lugar")
                    }
                }
            }
        }
    }
}

@Composable
private fun VistaSinPermiso(
    onActivarUbicacionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Activa tu ubicación para ver lugares cercanos",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "También puedes buscar una institución manualmente.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onActivarUbicacionClick, shape = MaterialTheme.shapes.medium) {
            Text("Activar ubicación")
        }
        Spacer(modifier = Modifier.height(24.dp))
        BarraBusqueda()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewMapaNormal() {
    GotaVidaTheme {
        MapaScreen(
            lugares = FakeLugaresDataSource.obtenerLugaresCercanos(),
            vistaLista = false,
            permisoUbicacionConcedido = true,
            lugarSeleccionado = null,
            onCambiarVista = {},
            onLugarClick = {},
            onCerrarDetalle = {},
            onActivarUbicacionClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewMapaConDetalle() {
    GotaVidaTheme {
        MapaScreen(
            lugares = FakeLugaresDataSource.obtenerLugaresCercanos(),
            vistaLista = false,
            permisoUbicacionConcedido = true,
            lugarSeleccionado = FakeLugaresDataSource.obtenerLugaresCercanos().first(),
            onCambiarVista = {},
            onLugarClick = {},
            onCerrarDetalle = {},
            onActivarUbicacionClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewMapaLista() {
    GotaVidaTheme {
        MapaScreen(
            lugares = FakeLugaresDataSource.obtenerLugaresCercanos(),
            vistaLista = true,
            permisoUbicacionConcedido = true,
            lugarSeleccionado = null,
            onCambiarVista = {},
            onLugarClick = {},
            onCerrarDetalle = {},
            onActivarUbicacionClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewMapaSinPermiso() {
    GotaVidaTheme {
        MapaScreen(
            lugares = emptyList(),
            vistaLista = false,
            permisoUbicacionConcedido = false,
            lugarSeleccionado = null,
            onCambiarVista = {},
            onLugarClick = {},
            onCerrarDetalle = {},
            onActivarUbicacionClick = {}
        )
    }
}
