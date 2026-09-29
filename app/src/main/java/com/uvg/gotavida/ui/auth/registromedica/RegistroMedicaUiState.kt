package com.uvg.gotavida.ui.auth.registromedica

enum class RegistroMedicaFase {
    FORMULARIO,
    EN_REVISION,
}

data class RegistroMedicaUiState(
    val fase: RegistroMedicaFase = RegistroMedicaFase.FORMULARIO,
    val nombreInstitucion: String = "",
    val correoInstitucional: String = "",
    val numeroColegiado: String = "", // opcional
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isContinueEnabled: Boolean
        get() = nombreInstitucion.isNotBlank() && correoInstitucional.isNotBlank() && !isLoading
}
