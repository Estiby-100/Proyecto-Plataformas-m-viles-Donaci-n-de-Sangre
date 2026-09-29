package com.uvg.gotavida.ui.auth.login

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Bienvenido de nuevo",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Inicia sesión para continuar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(32.dp))

        GotaVidaTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Correo electrónico",
            enabled = !uiState.isLoading,
            isError = uiState.errorMessage != null,
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
            isError = uiState.errorMessage != null,
            supportingText = uiState.errorMessage,
        )

        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                text = "¿Olvidaste tu contraseña?",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable { onForgotPasswordClick() },
            )
        }

        Spacer(Modifier.weight(1f))

        if (uiState.isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        } else {
            GotaVidaPrimaryButton(
                text = "Iniciar sesión",
                onClick = onLoginClick,
                enabled = uiState.isContinueEnabled,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "¿No tienes cuenta? ",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "Regístrate",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable { onRegisterClick() },
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

// --- Previews: uno por cada estado que pide la entrega ---

@Preview(showBackground = true, name = "Login - Normal (vacío, deshabilitado)")
@Composable
private fun LoginScreenPreviewNormal() {
    GotaVidaTheme {
        LoginScreen(
            uiState = LoginUiState(),
            onEmailChange = {}, onPasswordChange = {}, onTogglePasswordVisibility = {},
            onLoginClick = {}, onForgotPasswordClick = {}, onBackClick = {}, onRegisterClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Login - Campos llenos (habilitado)")
@Composable
private fun LoginScreenPreviewFilled() {
    GotaVidaTheme {
        LoginScreen(
            uiState = LoginUiState(email = "javier@uvg.edu.gt", password = "12345678"),
            onEmailChange = {}, onPasswordChange = {}, onTogglePasswordVisibility = {},
            onLoginClick = {}, onForgotPasswordClick = {}, onBackClick = {}, onRegisterClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Login - Cargando")
@Composable
private fun LoginScreenPreviewLoading() {
    GotaVidaTheme {
        LoginScreen(
            uiState = LoginUiState(
                email = "javier@uvg.edu.gt",
                password = "12345678",
                isLoading = true,
            ),
            onEmailChange = {}, onPasswordChange = {}, onTogglePasswordVisibility = {},
            onLoginClick = {}, onForgotPasswordClick = {}, onBackClick = {}, onRegisterClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Login - Error")
@Composable
private fun LoginScreenPreviewError() {
    GotaVidaTheme {
        LoginScreen(
            uiState = LoginUiState(
                email = "javier@uvg.edu.gt",
                password = "contraseñaMala",
                errorMessage = "Correo o contraseña incorrectos.",
            ),
            onEmailChange = {}, onPasswordChange = {}, onTogglePasswordVisibility = {},
            onLoginClick = {}, onForgotPasswordClick = {}, onBackClick = {}, onRegisterClick = {},
        )
    }
}
