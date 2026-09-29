package com.uvg.gotavida.ui.auth.registromedica

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.ui.components.GotaVidaPrimaryButton
import com.uvg.gotavida.ui.components.GotaVidaTextField
import com.uvg.gotavida.ui.theme.GotaVidaTheme

@Composable
fun RegistroMedicaScreen(
    uiState: RegistroMedicaUiState,
    onNombreInstitucionChange: (String) -> Unit,
    onCorreoInstitucionalChange: (String) -> Unit,
    onNumeroColegiadoChange: (String) -> Unit,
    onEnviarClick: () -> Unit,
    onVolverAlInicioClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState.fase) {
        RegistroMedicaFase.FORMULARIO -> FormularioContent(
            uiState = uiState,
            onNombreInstitucionChange = onNombreInstitucionChange,
            onCorreoInstitucionalChange = onCorreoInstitucionalChange,
            onNumeroColegiadoChange = onNumeroColegiadoChange,
            onEnviarClick = onEnviarClick,
            onBackClick = onBackClick,
            modifier = modifier,
        )
        RegistroMedicaFase.EN_REVISION -> EnRevisionContent(
            onVolverAlInicioClick = onVolverAlInicioClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun FormularioContent(
    uiState: RegistroMedicaUiState,
    onNombreInstitucionChange: (String) -> Unit,
    onCorreoInstitucionalChange: (String) -> Unit,
    onNumeroColegiadoChange: (String) -> Unit,
    onEnviarClick: () -> Unit,
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
            text = "Verifica tu cuenta institucional",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Esto nos permite confirmar que las solicitudes publicadas sean reales.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(24.dp))
        GotaVidaTextField(
            value = uiState.nombreInstitucion,
            onValueChange = onNombreInstitucionChange,
            label = "Nombre de la institución",
            enabled = !uiState.isLoading,
        )
        Spacer(Modifier.height(16.dp))
        GotaVidaTextField(
            value = uiState.correoInstitucional,
            onValueChange = onCorreoInstitucionalChange,
            label = "Correo institucional",
            enabled = !uiState.isLoading,
            isError = uiState.errorMessage != null,
            supportingText = uiState.errorMessage,
        )
        Spacer(Modifier.height(16.dp))
        GotaVidaTextField(
            value = uiState.numeroColegiado,
            onValueChange = onNumeroColegiadoChange,
            label = "Número de colegiado profesional (opcional)",
            enabled = !uiState.isLoading,
        )

        Spacer(Modifier.height(16.dp))
        TrustBanner()

        Spacer(Modifier.weight(1f))

        GotaVidaPrimaryButton(
            text = "Enviar para revisión",
            onClick = onEnviarClick,
            enabled = uiState.isContinueEnabled,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TrustBanner() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.large, // radio 20px
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Filled.Shield,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.padding(start = 8.dp))
            Text(
                text = "Nuestro equipo revisará esta información antes de activar tu cuenta.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun EnRevisionContent(
    onVolverAlInicioClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(72.dp),
        ) {
            androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Tu cuenta está en revisión",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Te avisaremos cuando esté activa.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Volver al inicio",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.clickable { onVolverAlInicioClick() },
        )
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Registro médica - Formulario (deshabilitado)")
@Composable
private fun RegistroMedicaScreenPreviewFormularioNormal() {
    GotaVidaTheme {
        RegistroMedicaScreen(
            uiState = RegistroMedicaUiState(),
            onNombreInstitucionChange = {}, onCorreoInstitucionalChange = {}, onNumeroColegiadoChange = {},
            onEnviarClick = {}, onVolverAlInicioClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro médica - Formulario completo (habilitado)")
@Composable
private fun RegistroMedicaScreenPreviewFormularioFilled() {
    GotaVidaTheme {
        RegistroMedicaScreen(
            uiState = RegistroMedicaUiState(
                nombreInstitucion = "Hospital Roosevelt",
                correoInstitucional = "contacto@hospitalroosevelt.gob.gt",
                numeroColegiado = "12345",
            ),
            onNombreInstitucionChange = {}, onCorreoInstitucionalChange = {}, onNumeroColegiadoChange = {},
            onEnviarClick = {}, onVolverAlInicioClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro médica - Formulario error")
@Composable
private fun RegistroMedicaScreenPreviewFormularioError() {
    GotaVidaTheme {
        RegistroMedicaScreen(
            uiState = RegistroMedicaUiState(
                nombreInstitucion = "Hospital Roosevelt",
                correoInstitucional = "correo-invalido",
                errorMessage = "Ingresa un correo institucional válido.",
            ),
            onNombreInstitucionChange = {}, onCorreoInstitucionalChange = {}, onNumeroColegiadoChange = {},
            onEnviarClick = {}, onVolverAlInicioClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro médica - En revisión")
@Composable
private fun RegistroMedicaScreenPreviewEnRevision() {
    GotaVidaTheme {
        RegistroMedicaScreen(
            uiState = RegistroMedicaUiState(fase = RegistroMedicaFase.EN_REVISION),
            onNombreInstitucionChange = {}, onCorreoInstitucionalChange = {}, onNumeroColegiadoChange = {},
            onEnviarClick = {}, onVolverAlInicioClick = {}, onBackClick = {},
        )
    }
}
