package com.uvg.gotavida.ui.auth.registromedica

enum class RegistroMedicaFase {
    FORMULARIO,
    EN_REVISION,
}

data class RegistroMedicaUiState(
    val fase: RegistroMedicaFase = RegistroMedicaFase.FORMULARIO,
    val nombreInstitucion: String = "",
    val correoInstitucional: String = "",
    val numeroColegiado: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isContinueEnabled: Boolean
        get() = nombreInstitucion.isNotBlank() && correoInstitucional.isNotBlank() && !isLoading
}

sealed interface RegistroMedicaEvent {
    data class Institucion(
        val nombre: String
    ) : RegistroMedicaEvent
    data class Correo(
        val correo: String
    ) : RegistroMedicaEvent
    data class NumeroColegiado(
        val numero: String
    ) : RegistroMedicaEvent
    data object Enviar : RegistroMedicaEvent
    data object Inicio : RegistroMedicaEvent
    data object Back : RegistroMedicaEvent
}
