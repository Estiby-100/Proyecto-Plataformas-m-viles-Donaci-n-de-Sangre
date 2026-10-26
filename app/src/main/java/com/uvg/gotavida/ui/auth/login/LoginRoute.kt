package com.uvg.gotavida.ui.auth.login

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.gotavida.data.model.RolCuenta

@Composable
fun LoginRoute(
    onLoginExitoso: (RolCuenta) -> Unit,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.rolAutenticado) {
        val rol = state.rolAutenticado ?: return@LaunchedEffect
        viewModel.onEvent(LoginEvent.NavegacionAtendida)
        onLoginExitoso(rol)
    }

    LoginScreen(
        modifier = Modifier.systemBarsPadding(),
        uiState = state,

        onEmailChange = {
            viewModel.onEvent(
                LoginEvent.EmailChanged(it)
            )
        },

        onPasswordChange = {
            viewModel.onEvent(
                LoginEvent.PasswordChanged(it)
            )
        },

        onTogglePasswordVisibility = {
            viewModel.onEvent(
                LoginEvent.Toggle
            )
        },

        onLoginClick = {
            viewModel.onEvent(
                LoginEvent.Login
            )
        },

        onForgotPasswordClick = {
            viewModel.onEvent(
                LoginEvent.Forgot
            )
        },

        onBackClick = {
            viewModel.onEvent(
                LoginEvent.Back
            )
            onBackClick()
        },

        onRegisterClick = {
            viewModel.onEvent(
                LoginEvent.Register
            )
            onRegisterClick()
        }
    )
}
