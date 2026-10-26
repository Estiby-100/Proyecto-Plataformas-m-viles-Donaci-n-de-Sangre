package com.uvg.gotavida.ui.auth.registrodonante

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistroDonanteRoute(
    onPerfilCompleto: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: RegistroDonanteViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.perfilCompleto) {
        if (!state.perfilCompleto) return@LaunchedEffect
        viewModel.onEvent(RegistroDonanteEvent.NavegacionAtendida)
        onPerfilCompleto()
    }

    RegistroDonanteScreen(

        uiState = state,

        onBloodTypeSelected = {
            viewModel.onEvent(
                RegistroDonanteEvent.BloodTypeSelected(it)
            )
        },

        onCiudadChange = {
            viewModel.onEvent(
                RegistroDonanteEvent.Ciudad(it)
            )
        },

        onContinueClick = {
            viewModel.onEvent(
                RegistroDonanteEvent.Continue
            )
        },

        onBackClick = {
            viewModel.onEvent(
                RegistroDonanteEvent.Back
            )
            onBackClick()
        }
    )
}
