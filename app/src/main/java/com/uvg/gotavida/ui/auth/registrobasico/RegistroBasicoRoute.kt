package com.uvg.gotavida.ui.auth.registrobasico

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistroBasicoRoute(
    viewModel: RegistroBasicoViewModel = viewModel()
) {

    val state = viewModel.state

    RegistroBasicoScreen(

        uiState = state,

        onNombreChange = {
            viewModel.onEvent(
                RegistroBasicoEvent.NombreChanged(it)
            )
        },

        onEmailChange = {
            viewModel.onEvent(
                RegistroBasicoEvent.EmailChanged(it)
            )
        },

        onPasswordChange = {
            viewModel.onEvent(
                RegistroBasicoEvent.PasswordChanged(it)
            )
        },

        onConfirmPasswordChange = {
            viewModel.onEvent(
                RegistroBasicoEvent.Confirm(it)
            )
        },

        onTogglePasswordVisibility = {
            viewModel.onEvent(
                RegistroBasicoEvent.Toggle
            )
        },

        onToggleConfirmPasswordVisibility = {
            viewModel.onEvent(
                RegistroBasicoEvent.ToggleConfirm
            )
        },

        onToggleAcceptedTerms = {
            viewModel.onEvent(
                RegistroBasicoEvent.ToggleAcceptedTerms
            )
        },

        onContinueClick = {
            viewModel.onEvent(
                RegistroBasicoEvent.Continue
            )
        },

        onBackClick = {
            viewModel.onEvent(
                RegistroBasicoEvent.Back
            )
        },

        onTermsClick = {
            viewModel.onEvent(
                RegistroBasicoEvent.OpenTerms
            )
        },

        onPrivacyClick = {
            viewModel.onEvent(
                RegistroBasicoEvent.OpenPrivacy
            )
        }
    )
}
