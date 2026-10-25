package com.uvg.gotavida.ui.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val isContinueEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && !isLoading
}

sealed interface LoginEvent {
    data class EmailChanged(
        val email: String
    ) : LoginEvent
    data class PasswordChanged(
        val password: String
    ) : LoginEvent
    data object Toggle : LoginEvent
    data object Login : LoginEvent
    data object Forgot : LoginEvent
    data object Register : LoginEvent
    data object Back : LoginEvent
}
