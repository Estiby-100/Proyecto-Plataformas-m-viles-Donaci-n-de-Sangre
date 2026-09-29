package com.uvg.gotavida.ui.auth.login

/**
 * Todo lo que la pantalla de Login necesita para dibujarse en cualquier
 * momento. El Screen es una función pura de este estado — no tiene su
 * propia lógica de validación ni de red.
 */
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
