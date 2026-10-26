package com.uvg.gotavida.ui.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.auth.CuentaRepository
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val cuentas: CuentaRepository = ServiceLocator.cuentaRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SplashUiState())
    val state: StateFlow<SplashUiState> = _state.asStateFlow()

    init {
        verificarSesion()
    }

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.SesionAtendida -> _state.update { it.copy(sesionActiva = null) }

            // Elegir rol o ir a login no cambia el estado: la vista navega.
            SplashEvent.DonanteClick,
            SplashEvent.MedicaClick,
            SplashEvent.LoginClick -> Unit
        }
    }

    private fun verificarSesion() {
        viewModelScope.launch {
            val usuario = cuentas.sesionActual()
            _state.update {
                it.copy(
                    verificandoSesion = false,
                    sesionActiva = usuario?.rol,
                )
            }
        }
    }
}
