package com.uvg.gotavida.ui.auth.registrobasico

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun RegistroBasicoRoute() {
    var uiState by remember { mutableStateOf(RegistroBasicoUiState()) }

    RegistroBasicoScreen(
        uiState = uiState,
        onNombreChange = { uiState = uiState.copy(nombreCompleto = it) },
        onEmailChange = { uiState = uiState.copy(email = it) },
        onPasswordChange = { uiState = uiState.copy(password = it) },
        onConfirmPasswordChange = { uiState = uiState.copy(confirmPassword = it) },
        onTogglePasswordVisibility = { uiState = uiState.copy(passwordVisible = !uiState.passwordVisible) },
        onToggleConfirmPasswordVisibility = {
            uiState = uiState.copy(confirmPasswordVisible = !uiState.confirmPasswordVisible)
        },
        onToggleAcceptedTerms = { uiState = uiState.copy(acceptedTerms = !uiState.acceptedTerms) },
        onTermsClick = { /* TODO: pantalla de Términos, pendiente de aprobación */ },
        onPrivacyClick = { /* TODO: pantalla de Política de Privacidad, pendiente de aprobación */ },
        onContinueClick = { /* TODO: navegar a Registro donante o Registro médica según el rol elegido en Splash */ },
        onBackClick = { /* TODO: navegar a Splash */ },
    )
}
