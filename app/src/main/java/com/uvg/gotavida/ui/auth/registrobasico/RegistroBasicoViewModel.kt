package com.uvg.gotavida.ui.auth.registrobasico

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class RegistroBasicoViewModel : ViewModel() {

    var state by mutableStateOf(
        RegistroBasicoUiState()
    )
        private set

    fun onEvent(event: RegistroBasicoEvent) {

        when (event) {

            is RegistroBasicoEvent.NombreChanged -> {

                state = state.copy(
                    nombreCompleto = event.nombre
                )
            }

            is RegistroBasicoEvent.EmailChanged -> {

                state = state.copy(
                    email = event.email
                )
            }

            is RegistroBasicoEvent.PasswordChanged -> {

                state = state.copy(
                    password = event.password
                )
            }

            is RegistroBasicoEvent.Confirm -> {

                state = state.copy(
                    confirmPassword = event.password
                )
            }

            RegistroBasicoEvent.Toggle -> {

                state = state.copy(
                    passwordVisible = !state.passwordVisible
                )
            }

            RegistroBasicoEvent.ToggleConfirm -> {

                state = state.copy(
                    confirmPasswordVisible =
                        !state.confirmPasswordVisible
                )
            }

            RegistroBasicoEvent.ToggleAcceptedTerms -> {

                state = state.copy(
                    acceptedTerms = !state.acceptedTerms
                )
            }

            RegistroBasicoEvent.Continue -> {

                if (state.password != state.confirmPassword) {

                    state = state.copy(
                        errorMessage =
                            "Las contraseñas no coinciden"
                    )
                }
            }

            RegistroBasicoEvent.Back -> Unit
            RegistroBasicoEvent.OpenTerms -> Unit
            RegistroBasicoEvent.OpenPrivacy -> Unit
        }
    }
}