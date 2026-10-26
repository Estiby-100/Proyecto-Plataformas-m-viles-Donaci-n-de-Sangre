package com.uvg.gotavida.ui.auth.registrobasico

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.gotavida.data.model.RolCuenta

@Composable
fun RegistroBasicoRoute(
    rol: RolCuenta,
    onCuentaCreada: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: RegistroBasicoViewModel = viewModel()
) {

    LaunchedEffect(rol) {
        viewModel.onEvent(RegistroBasicoEvent.RolAsignado(rol))
    }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.cuentaCreada) {
        if (!state.cuentaCreada) return@LaunchedEffect
        viewModel.onEvent(RegistroBasicoEvent.NavegacionAtendida)
        onCuentaCreada()
    }

    RegistroBasicoScreen(

        modifier = Modifier.systemBarsPadding(),

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
            onBackClick()
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
