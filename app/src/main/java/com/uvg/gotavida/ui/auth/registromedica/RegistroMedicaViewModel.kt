package com.uvg.gotavida.ui.auth.registromedica

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

class RegistroMedicaViewModel(
    private val cuentas: CuentaRepository = ServiceLocator.cuentaRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RegistroMedicaUiState())
    val state: StateFlow<RegistroMedicaUiState> = _state.asStateFlow()

    fun onEvent(event: RegistroMedicaEvent) {

        when (event) {

            is RegistroMedicaEvent.Institucion -> {
                _state.update { it.copy(nombreInstitucion = event.nombre, errorMessage = null) }
            }

            is RegistroMedicaEvent.Correo -> {
                _state.update { it.copy(correoInstitucional = event.correo, errorMessage = null) }
            }

            is RegistroMedicaEvent.NumeroColegiado -> {
                _state.update { it.copy(numeroColegiado = event.numero) }
            }

            RegistroMedicaEvent.Enviar -> enviarARevision()

            RegistroMedicaEvent.Inicio -> {
                // La cuenta queda en revisión: se cierra la sesión y se vuelve a Splash.
                // Al dashboard médico solo se entra iniciando sesión.
                cuentas.cerrarSesion()
                _state.update { it.copy(volverAlInicio = true) }
            }

            RegistroMedicaEvent.NavegacionAtendida -> {
                _state.update { it.copy(volverAlInicio = false) }
            }

            RegistroMedicaEvent.Back -> Unit
        }
    }

    private fun enviarARevision() {
        val actual = _state.value
        if (!actual.isContinueEnabled) return

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val resultado = cuentas.completarPerfilMedica(
                institucion = actual.nombreInstitucion,
                numeroColegiado = actual.numeroColegiado,
            )
            when (resultado) {
                is AuthResult.Exito -> _state.update {
                    it.copy(isLoading = false, fase = RegistroMedicaFase.EN_REVISION)
                }
                is AuthResult.Fallo -> _state.update {
                    it.copy(isLoading = false, errorMessage = resultado.error.aMensaje())
                }
            }
        }
    }
}
