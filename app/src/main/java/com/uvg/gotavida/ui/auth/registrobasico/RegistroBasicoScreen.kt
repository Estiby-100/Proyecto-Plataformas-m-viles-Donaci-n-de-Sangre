package com.uvg.gotavida.ui.auth.registrobasico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.ui.components.GotaVidaPrimaryButton
import com.uvg.gotavida.ui.components.GotaVidaTextField
import com.uvg.gotavida.ui.theme.GotaVidaTheme

@Composable
fun RegistroBasicoScreen(
    uiState: RegistroBasicoUiState,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit,
    onToggleAcceptedTerms: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
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
        // Top bar: flecha + progreso
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
        }
        LinearProgressIndicator(
            progress = { 0.5f },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outline,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Paso 1 de 2",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Cuéntanos sobre ti",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(24.dp))

        GotaVidaTextField(
            value = uiState.nombreCompleto,
            onValueChange = onNombreChange,
            label = "Nombre completo",
            enabled = !uiState.isLoading,
        )
        Spacer(Modifier.height(16.dp))
        GotaVidaTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Correo electrónico",
            enabled = !uiState.isLoading,
        )
        Spacer(Modifier.height(16.dp))
        GotaVidaTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            isPassword = true,
            passwordVisible = uiState.passwordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            enabled = !uiState.isLoading,
        )
        Spacer(Modifier.height(16.dp))
        GotaVidaTextField(
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirmar contraseña",
            isPassword = true,
            passwordVisible = uiState.confirmPasswordVisible,
            onTogglePasswordVisibility = onToggleConfirmPasswordVisibility,
            enabled = !uiState.isLoading,
            isError = uiState.errorMessage != null,
            supportingText = uiState.errorMessage,
        )

        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = uiState.acceptedTerms,
                onCheckedChange = { onToggleAcceptedTerms() },
                enabled = !uiState.isLoading,
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
            )
            TermsAndPrivacyText(onTermsClick = onTermsClick, onPrivacyClick = onPrivacyClick)
        }

        Spacer(Modifier.weight(1f))

        GotaVidaPrimaryButton(
            text = "Continuar",
            onClick = onContinueClick,
            enabled = uiState.isContinueEnabled,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TermsAndPrivacyText(onTermsClick: () -> Unit, onPrivacyClick: () -> Unit) {
    val linkColor = MaterialTheme.colorScheme.secondary
    val textColor = MaterialTheme.colorScheme.onBackground
    val annotated = buildAnnotatedString {
        append("Acepto los ")
        pushStringAnnotation(tag = "terminos", annotation = "terminos")
        withStyle(SpanStyle(color = linkColor)) { append("Términos") }
        pop()
        append(" y la ")
        pushStringAnnotation(tag = "privacidad", annotation = "privacidad")
        withStyle(SpanStyle(color = linkColor)) { append("Política de Privacidad") }
        pop()
    }
    ClickableText(
        text = annotated,
        style = MaterialTheme.typography.labelSmall.copy(color = textColor),
        onClick = { offset ->
            annotated.getStringAnnotations("terminos", offset, offset).firstOrNull()?.let { onTermsClick() }
            annotated.getStringAnnotations("privacidad", offset, offset).firstOrNull()?.let { onPrivacyClick() }
        },
    )
}

// --- Previews ---

@Preview(showBackground = true, name = "Registro básico - Normal (deshabilitado)")
@Composable
private fun RegistroBasicoScreenPreviewNormal() {
    GotaVidaTheme {
        RegistroBasicoScreen(
            uiState = RegistroBasicoUiState(),
            onNombreChange = {}, onEmailChange = {}, onPasswordChange = {}, onConfirmPasswordChange = {},
            onTogglePasswordVisibility = {}, onToggleConfirmPasswordVisibility = {},
            onToggleAcceptedTerms = {}, onTermsClick = {}, onPrivacyClick = {},
            onContinueClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro básico - Completo (habilitado)")
@Composable
private fun RegistroBasicoScreenPreviewFilled() {
    GotaVidaTheme {
        RegistroBasicoScreen(
            uiState = RegistroBasicoUiState(
                nombreCompleto = "Javier Sánchez",
                email = "javier@uvg.edu.gt",
                password = "12345678",
                confirmPassword = "12345678",
                acceptedTerms = true,
            ),
            onNombreChange = {}, onEmailChange = {}, onPasswordChange = {}, onConfirmPasswordChange = {},
            onTogglePasswordVisibility = {}, onToggleConfirmPasswordVisibility = {},
            onToggleAcceptedTerms = {}, onTermsClick = {}, onPrivacyClick = {},
            onContinueClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro básico - Error (contraseñas no coinciden)")
@Composable
private fun RegistroBasicoScreenPreviewError() {
    GotaVidaTheme {
        RegistroBasicoScreen(
            uiState = RegistroBasicoUiState(
                nombreCompleto = "Javier Sánchez",
                email = "javier@uvg.edu.gt",
                password = "12345678",
                confirmPassword = "87654321",
                acceptedTerms = true,
                errorMessage = "Las contraseñas no coinciden.",
            ),
            onNombreChange = {}, onEmailChange = {}, onPasswordChange = {}, onConfirmPasswordChange = {},
            onTogglePasswordVisibility = {}, onToggleConfirmPasswordVisibility = {},
            onToggleAcceptedTerms = {}, onTermsClick = {}, onPrivacyClick = {},
            onContinueClick = {}, onBackClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Registro básico - Cargando")
@Composable
private fun RegistroBasicoScreenPreviewLoading() {
    GotaVidaTheme {
        RegistroBasicoScreen(
            uiState = RegistroBasicoUiState(
                nombreCompleto = "Javier Sánchez",
                email = "javier@uvg.edu.gt",
                password = "12345678",
                confirmPassword = "12345678",
                acceptedTerms = true,
                isLoading = true,
            ),
            onNombreChange = {}, onEmailChange = {}, onPasswordChange = {}, onConfirmPasswordChange = {},
            onTogglePasswordVisibility = {}, onToggleConfirmPasswordVisibility = {},
            onToggleAcceptedTerms = {}, onTermsClick = {}, onPrivacyClick = {},
            onContinueClick = {}, onBackClick = {},
        )
    }
}
