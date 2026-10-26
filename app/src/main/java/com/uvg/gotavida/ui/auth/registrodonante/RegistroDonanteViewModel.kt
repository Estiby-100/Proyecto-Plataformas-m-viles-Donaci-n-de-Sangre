package com.uvg.gotavida.ui.auth.registrodonante

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

class RegistroDonanteViewModel(
    private val cuentas: CuentaRepository = ServiceLocator.cuentaRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RegistroDonanteUiState())
    val state: StateFlow<RegistroDonanteUiState> = _state.asStateFlow()

    fun onEvent(event: RegistroDonanteEvent) {

        when (event) {

            is RegistroDonanteEvent.BloodTypeSelected -> {
                _state.update { it.copy(selectedBloodType = event.tipo) }
            }

            is RegistroDonanteEvent.Ciudad -> {
                _state.update { it.copy(ciudadOZona = event.ciudad, errorMessage = null) }
            }

            RegistroDonanteEvent.Continue -> guardarPerfil()

            RegistroDonanteEvent.NavegacionAtendida -> {
                _state.update { it.copy(perfilCompleto = false) }
            }

            RegistroDonanteEvent.Back -> Unit
        }
    }

    private fun guardarPerfil() {
        val actual = _state.value
        if (!actual.isContinueEnabled) return

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            when (val resultado = cuentas.completarPerfilDonante(actual.selectedBloodType, actual.ciudadOZona)) {
                is AuthResult.Exito -> _state.update {
                    it.copy(isLoading = false, perfilCompleto = true)
                }
                is AuthResult.Fallo -> _state.update {
                    it.copy(isLoading = false, errorMessage = resultado.error.aMensaje())
                }
            }
        }
    }
}
