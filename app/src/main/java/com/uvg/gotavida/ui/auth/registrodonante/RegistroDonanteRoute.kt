package com.uvg.gotavida.ui.auth.registrodonante

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun RegistroDonanteRoute() {
    var uiState by remember { mutableStateOf(RegistroDonanteUiState()) }

    RegistroDonanteScreen(
        uiState = uiState,
        onBloodTypeSelected = { uiState = uiState.copy(selectedBloodType = it) },
        onCiudadChange = { uiState = uiState.copy(ciudadOZona = it) },
        onContinueClick = { /* TODO: crear cuenta y navegar a Home donante */ },
        onBackClick = { /* TODO: navegar a Registro básico */ },
    )
}
