package com.uvg.gotavida.ui.auth.registrodonante

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistroDonanteRoute(
    viewModel: RegistroDonanteViewModel = viewModel()
) {

    val state = viewModel.state

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
        }
    )
}