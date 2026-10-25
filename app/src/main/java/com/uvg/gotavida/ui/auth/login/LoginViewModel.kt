package com.uvg.gotavida.ui.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class LoginViewModel : ViewModel() {

    var state by mutableStateOf(
        LoginUiState()
    )
        private set

    fun onEvent(event: LoginEvent) {

        when (event) {

            is LoginEvent.EmailChanged -> {

                state = state.copy(
                    email = event.email,
                    errorMessage = null
                )
            }

            is LoginEvent.PasswordChanged -> {

                state = state.copy(
                    password = event.password,
                    errorMessage = null
                )
            }

            LoginEvent.Toggle -> {

                state = state.copy(
                    passwordVisible =
                        !state.passwordVisible
                )
            }

            LoginEvent.Login -> {
                // navegacion
            }

            LoginEvent.Register -> Unit

            LoginEvent.Forgot -> Unit

            LoginEvent.Back -> Unit
        }
    }
}