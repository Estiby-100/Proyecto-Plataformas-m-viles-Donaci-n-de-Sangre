package com.uvg.gotavida.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.auth.AuthResult
import com.uvg.gotavida.data.auth.CuentaRepository
import com.uvg.gotavida.di.ServiceLocator
import com.uvg.gotavida.ui.auth.aMensaje
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val cuentas: CuentaRepository = ServiceLocator.cuentaRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {

        when (event) {

            is LoginEvent.EmailChanged -> {
                _state.update {
                    it.copy(email = event.email, errorMessage = null)
                }
            }

            is LoginEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = event.password, errorMessage = null)
                }
            }

            LoginEvent.Toggle -> {
                _state.update {
                    it.copy(passwordVisible = !it.passwordVisible)
                }
            }

            LoginEvent.Login -> iniciarSesion()

            LoginEvent.NavegacionAtendida -> {
                _state.update { it.copy(rolAutenticado = null) }
            }

            // Solo navegan; no cambian el estado.
            LoginEvent.Register -> Unit
            LoginEvent.Forgot -> Unit
            LoginEvent.Back -> Unit
        }
    }

    private fun iniciarSesion() {
        val actual = _state.value
        if (!actual.isContinueEnabled) return

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            when (val resultado = cuentas.iniciarSesion(actual.email, actual.password)) {
                is AuthResult.Exito -> _state.update {
                    it.copy(isLoading = false, rolAutenticado = resultado.valor.rol)
                }
                is AuthResult.Fallo -> _state.update {
                    it.copy(isLoading = false, errorMessage = resultado.error.aMensaje())
                }
            }
        }
    }
}
