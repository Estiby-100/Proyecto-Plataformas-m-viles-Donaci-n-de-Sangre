package com.uvg.gotavida.ui.donante.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * Diálogo de contacto institucional, reutilizado en la Confirmación (9) y en
 * el resultado "posiblemente no apto" del Cuestionario (8).
 *
 * El número de teléfono es siempre demostrativo (fake data source) hasta que
 * exista un contacto institucional verificado real.
 */
@Composable
fun DialogoContactoInstitucion(
    institucion: String,
    telefono: String,
    onLlamarClick: () -> Unit,
    onCancelarClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelarClick,
        title = { Text(institucion) },
        text = { Text("Tel: $telefono") },
        confirmButton = {
            TextButton(onClick = onLlamarClick) { Text("Llamar") }
        },
        dismissButton = {
            TextButton(onClick = onCancelarClick) { Text("Cancelar") }
        }
    )
}
