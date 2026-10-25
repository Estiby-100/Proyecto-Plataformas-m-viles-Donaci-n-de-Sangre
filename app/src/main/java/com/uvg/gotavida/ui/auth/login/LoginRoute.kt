package com.uvg.gotavida.ui.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = viewModel()
) {

    val state = viewModel.state

    LoginScreen(
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
        },

        onRegisterClick = {
            viewModel.onEvent(
                LoginEvent.Register
            )
        }
    )
}