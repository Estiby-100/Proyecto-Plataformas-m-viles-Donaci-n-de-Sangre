package com.uvg.gotavida.ui.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * En esta fase no hay lógica real de autenticación ni navegación — el
 * Route solo sostiene el estado de los campos de texto para que la
 * pantalla se sienta interactiva al correrla. El día que se agregue
 * lógica real, aquí es donde entraría el ViewModel (no dentro del Screen).
 */
@Composable
fun LoginRoute() {
    var uiState by remember { mutableStateOf(LoginUiState()) }

    LoginScreen(
        uiState = uiState,
        onEmailChange = { uiState = uiState.copy(email = it, errorMessage = null) },
        onPasswordChange = { uiState = uiState.copy(password = it, errorMessage = null) },
        onTogglePasswordVisibility = {
            uiState = uiState.copy(passwordVisible = !uiState.passwordVisible)
        },
        onLoginClick = { /* TODO: autenticar y navegar según rol */ },
        onForgotPasswordClick = { /* TODO: pendiente de aprobación del equipo */ },
        onBackClick = { /* TODO: navegar a Splash */ },
        onRegisterClick = { /* TODO: navegar a Splash */ },
    )
}
