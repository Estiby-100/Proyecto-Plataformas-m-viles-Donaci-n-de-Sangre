package com.uvg.gotavida.ui.auth.registrobasico

import com.uvg.gotavida.data.model.RolCuenta

data class RegistroBasicoUiState(
    /** Rol elegido en Splash; llega como argumento de navegación. */
    val rol: RolCuenta = RolCuenta.DONANTE,
    val nombreCompleto: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,
    val acceptedTerms: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    /** true cuando la cuenta ya se creó; la vista navega al siguiente paso. */
    val cuentaCreada: Boolean = false,
) {
    val isContinueEnabled: Boolean
        get() = nombreCompleto.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            acceptedTerms &&
            !isLoading
}

sealed interface RegistroBasicoEvent {
    data class RolAsignado(
        val rol: RolCuenta
    ) : RegistroBasicoEvent
    data class NombreChanged(
        val nombre: String
    ) : RegistroBasicoEvent
    data class EmailChanged(
        val email: String
    ) : RegistroBasicoEvent
    data class PasswordChanged(
        val password: String
    ) : RegistroBasicoEvent
    data class Confirm(
        val password: String
    ) : RegistroBasicoEvent
    data object Toggle : RegistroBasicoEvent
    data object ToggleConfirm : RegistroBasicoEvent
    data object ToggleAcceptedTerms : RegistroBasicoEvent
    data object Continue : RegistroBasicoEvent
    data object Back : RegistroBasicoEvent
    data object OpenTerms : RegistroBasicoEvent
    data object OpenPrivacy : RegistroBasicoEvent
    data object NavegacionAtendida : RegistroBasicoEvent
}
