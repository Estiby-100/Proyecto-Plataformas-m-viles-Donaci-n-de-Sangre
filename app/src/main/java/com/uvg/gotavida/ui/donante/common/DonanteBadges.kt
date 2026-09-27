package com.uvg.gotavida.ui.donante.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.model.NivelUrgencia

/**
 * Badge de tipo de sangre. Usa siempre el par contenedor/on-contenedor
 * (fondo suave + texto oscuro accesible), nunca el coral saturado con texto
 * blanco pequeño encima.
 */
@Composable
fun BadgeTipoSangre(
    tipoSangre: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
    ) {
        Text(
            text = tipoSangre,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

/**
 * Chip de urgencia ("Urgente" en coral suave, "Programada"/"Jornada" en teal).
 */
@Composable
fun ChipUrgencia(
    urgencia: NivelUrgencia,
    esJornada: Boolean = false,
    modifier: Modifier = Modifier
) {
    val (color, onColor, texto) = when {
        esJornada -> Triple(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            "Jornada"
        )
        urgencia == NivelUrgencia.URGENTE -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            "Urgente"
        )
        else -> Triple(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            "Programada"
        )
    }
    Surface(
        color = color,
        contentColor = onColor,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

/**
 * Insignia "Verificado" junto al nombre de una institución. Se reutiliza en
 * la tarjeta de Home, el Detalle y la Confirmación.
 */
@Composable
fun BadgeVerificado(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Institución verificada",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = "Verificado",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

/** Ícono circular de institución (placeholder mientras no hay logos reales). */
@Composable
fun IconoInstitucion(
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = CircleShape,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.AccountBalance,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(8.dp)
        )
    }
}
