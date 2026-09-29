package com.uvg.gotavida.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Estado deshabilitado: no tiene rol propio en el colorScheme compartido,
// así que se define aquí, local a este componente (no se toca ui/theme/).
private val DisabledBg = Color(0xFFD8D5D0)
private val DisabledText = Color(0xFF9A968F)

/**
 * Botón primario: fondo coral (#C94332), texto blanco. Usado para la acción
 * principal de cada pantalla (Continuar, Iniciar sesión, Crear mi cuenta...).
 */
@Composable
fun GotaVidaPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(48.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = DisabledBg,
            disabledContentColor = DisabledText,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

/**
 * Botón secundario: outline teal (#147A70), sin relleno.
 */
@Composable
fun GotaVidaSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(48.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary,
            disabledContentColor = DisabledText,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}
