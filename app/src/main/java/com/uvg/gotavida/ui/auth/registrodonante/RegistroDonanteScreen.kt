package com.uvg.gotavida.ui.auth.registrodonante

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.model.TipoSangre
import com.uvg.gotavida.ui.components.BloodTypeChip
import com.uvg.gotavida.ui.components.GotaVidaPrimaryButton
import com.uvg.gotavida.ui.components.GotaVidaTextField
import com.uvg.gotavida.ui.theme.GotaVidaTheme

@Composable
fun RegistroDonanteScreen(
    uiState: RegistroDonanteUiState,
    onBloodTypeSelected: (TipoSangre) -> Unit,
    onCiudadChange: (String) -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
        }
        LinearProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outline,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Paso 2 de 2",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Un último paso",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Esto nos ayuda a mostrarte solicitudes compatibles cerca de ti.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(24.dp))
        Text(
            text = "TIPO DE SANGRE",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(8.dp))

        val fila1 = listOf(TipoSangre.O_POS, TipoSangre.O_NEG, TipoSangre.A_POS, TipoSangre.A_NEG)
        val fila2 = listOf(TipoSangre.B_POS, TipoSangre.B_NEG, TipoSangre.AB_POS, TipoSangre.AB_NEG)

        BloodTypeRow(fila1, uiState.selectedBloodType, onBloodTypeSelected)
        Spacer(Modifier.height(8.dp))
        BloodTypeRow(fila2, uiState.selectedBloodType, onBloodTypeSelected)

        Spacer(Modifier.height(8.dp))
        Text(
            text = "Si no conoces tu tipo de sangre, puedes confirmarlo en tu primera donación.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(24.dp))
        GotaVidaTextField(
            value = uiState.ciudadOZona,
            onValueChange = onCiudadChange,
            label = "Ciudad o zona",
            leadingIcon = Icons.Filled.LocationOn,
            enabled = !uiState.isLoading,
            isError = uiState.errorMessage != null,
            supportingText = uiState.errorMessage,
        )

        Spacer(Modifier.weight(1f))

        GotaVidaPrimaryButton(
            text = "Crear mi cuenta",
            onClick = onContinueClick,
            enabled = uiState.isContinueEnabled,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun BloodTypeRow(
    tipos: List<TipoSangre>,
    selected: TipoSangre?,
    onSelected: (TipoSangre) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tipos.forEach { tipo ->
            BloodTypeChip(
                tipoSangre = tipo,
                selected = selected == tipo,
                onClick = { onSelected(tipo) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Registro donante - Normal (sin ubicación, deshabilitado)")
@Composable
private fun RegistroDonanteScreenPreviewNormal() {
    GotaVidaTheme {
        RegistroDonanteScreen(
            uiState = RegistroDonanteUiState(),
            onBloodTypeSelected = {}, onCiudadChange = {}, onContinueClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro donante - Completo (habilitado)")
@Composable
private fun RegistroDonanteScreenPreviewFilled() {
    GotaVidaTheme {
        RegistroDonanteScreen(
            uiState = RegistroDonanteUiState(
                selectedBloodType = TipoSangre.O_NEG,
                ciudadOZona = "Ciudad de Guatemala, Zona 10",
            ),
            onBloodTypeSelected = {}, onCiudadChange = {}, onContinueClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro donante - Cargando")
@Composable
private fun RegistroDonanteScreenPreviewLoading() {
    GotaVidaTheme {
        RegistroDonanteScreen(
            uiState = RegistroDonanteUiState(
                selectedBloodType = TipoSangre.O_NEG,
                ciudadOZona = "Ciudad de Guatemala, Zona 10",
                isLoading = true,
            ),
            onBloodTypeSelected = {}, onCiudadChange = {}, onContinueClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro donante - Error")
@Composable
private fun RegistroDonanteScreenPreviewError() {
    GotaVidaTheme {
        RegistroDonanteScreen(
            uiState = RegistroDonanteUiState(
                selectedBloodType = TipoSangre.O_NEG,
                ciudadOZona = "xyz",
                errorMessage = "No pudimos crear tu cuenta. Intenta de nuevo.",
            ),
            onBloodTypeSelected = {}, onCiudadChange = {}, onContinueClick = {}, onBackClick = {},
        )
    }
}
