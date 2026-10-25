package com.uvg.gotavida.ui.auth.registrodonante

import com.uvg.gotavida.data.model.TipoSangre

data class RegistroDonanteUiState(
    val selectedBloodType: TipoSangre? = null,
    val ciudadOZona: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isContinueEnabled: Boolean
        get() = ciudadOZona.isNotBlank() && !isLoading
}

sealed interface RegistroDonanteEvent {
    data class BloodTypeSelected(
        val tipo: TipoSangre
    ) : RegistroDonanteEvent
    data class Ciudad(
        val ciudad: String
    ) : RegistroDonanteEvent
    data object Continue : RegistroDonanteEvent
    data object Back : RegistroDonanteEvent
}