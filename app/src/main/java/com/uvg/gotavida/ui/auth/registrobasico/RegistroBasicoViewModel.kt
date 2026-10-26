package com.uvg.gotavida.ui.auth.registrobasico

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

class RegistroBasicoViewModel(
    private val cuentas: CuentaRepository = ServiceLocator.cuentaRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RegistroBasicoUiState())
    val state: StateFlow<RegistroBasicoUiState> = _state.asStateFlow()

    fun onEvent(event: RegistroBasicoEvent) {

        when (event) {

            is RegistroBasicoEvent.RolAsignado -> {
                _state.update { it.copy(rol = event.rol) }
            }

            is RegistroBasicoEvent.NombreChanged -> {
                _state.update { it.copy(nombreCompleto = event.nombre) }
            }

            is RegistroBasicoEvent.EmailChanged -> {
                _state.update { it.copy(email = event.email, errorMessage = null) }
            }

            is RegistroBasicoEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.password, errorMessage = null) }
            }

            is RegistroBasicoEvent.Confirm -> {
                _state.update { it.copy(confirmPassword = event.password, errorMessage = null) }
            }

            RegistroBasicoEvent.Toggle -> {
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }
            }

            RegistroBasicoEvent.ToggleConfirm -> {
                _state.update { it.copy(confirmPasswordVisible = !it.confirmPasswordVisible) }
            }

            RegistroBasicoEvent.ToggleAcceptedTerms -> {
                _state.update { it.copy(acceptedTerms = !it.acceptedTerms) }
            }

            RegistroBasicoEvent.Continue -> crearCuenta()

            RegistroBasicoEvent.NavegacionAtendida -> {
                _state.update { it.copy(cuentaCreada = false) }
            }

            RegistroBasicoEvent.Back -> Unit
            RegistroBasicoEvent.OpenTerms -> Unit
            RegistroBasicoEvent.OpenPrivacy -> Unit
        }
    }

    private fun crearCuenta() {
        val actual = _state.value
        if (!actual.isContinueEnabled) return

        if (actual.password != actual.confirmPassword) {
            _state.update { it.copy(errorMessage = "Las contraseñas no coinciden") }
            return
        }

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val resultado = cuentas.registrarCuenta(
                nombre = actual.nombreCompleto,
                correo = actual.email,
                contrasena = actual.password,
                rol = actual.rol,
            )
            when (resultado) {
                is AuthResult.Exito -> _state.update {
                    it.copy(isLoading = false, cuentaCreada = true)
                }
                is AuthResult.Fallo -> _state.update {
                    it.copy(isLoading = false, errorMessage = resultado.error.aMensaje())
                }
            }
        }
    }
}
