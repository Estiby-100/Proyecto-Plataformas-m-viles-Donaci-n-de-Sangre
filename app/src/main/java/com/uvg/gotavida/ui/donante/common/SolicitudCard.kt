package com.uvg.gotavida.ui.donante.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.model.SolicitudDonacion
import com.uvg.gotavida.data.model.TipoPublicacion
import com.uvg.gotavida.ui.theme.GotaVidaTheme

/**
 * Tarjeta de solicitud/jornada reutilizada en el feed de Home (pantalla 6) y,
 * en su variante compacta (sin botón), en la Confirmación (pantalla 9).
 *
 * El botón cambia de texto según el tipo de publicación: una solicitud de
 * sangre lleva a su Detalle ("Ver detalle"); una jornada lleva al Mapa
 * ("Ver en mapa"), nunca al Detalle de una solicitud puntual.
 */
@Composable
fun SolicitudCard(
    solicitud: SolicitudDonacion,
    onAccionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val esJornada = solicitud.tipoPublicacion == TipoPublicacion.JORNADA

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = MaterialTheme.shapes.large,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconoInstitucion(modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = solicitud.institucion,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (solicitud.institucionVerificada) {
                    BadgeVerificado()
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!esJornada) {
                    BadgeTipoSangre(tipoSangre = solicitud.tipoSangre)
                }
                ChipUrgencia(urgencia = solicitud.urgencia, esJornada = esJornada)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${solicitud.distanciaKm} km · ${solicitud.vigenciaTexto}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (onAccionClick != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onAccionClick,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(if (esJornada) "Ver en mapa" else "Ver detalle")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSolicitudCardUrgente() {
    GotaVidaTheme {
        SolicitudCard(
            solicitud = com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
                .obtenerSolicitudesUrgentesCompatibles().first(),
            onAccionClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSolicitudCardJornada() {
    GotaVidaTheme {
        SolicitudCard(
            solicitud = com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
                .obtenerJornadasCercanas().first(),
            onAccionClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
