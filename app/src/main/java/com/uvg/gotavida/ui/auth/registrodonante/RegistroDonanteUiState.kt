package com.uvg.gotavida.ui.auth.registrodonante

import com.uvg.gotavida.data.model.TipoSangre

data class RegistroDonanteUiState(
    val selectedBloodType: TipoSangre? = null, // puede quedar sin elegir, según el sistema
    val ciudadOZona: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isContinueEnabled: Boolean
        get() = ciudadOZona.isNotBlank() && !isLoading
}
