package com.uvg.gotavida.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.model.TipoSangre

/**
 * Chip pill seleccionable para un tipo de sangre. Reutiliza primaryContainer/
 * onPrimaryContainer del tema compartido (ya son los tonos coral suaves del
 * sistema, mapeados por el equipo en ui/theme/Theme.kt).
 */
@Composable
fun BloodTypeChip(
    tipoSangre: TipoSangre,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val border = if (selected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null

    Surface(
        shape = CircleShape,
        color = backgroundColor,
        border = border,
        modifier = modifier.clickable { onClick() },
    ) {
        Text(
            text = tipoSangre.etiqueta,
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        )
    }
}
