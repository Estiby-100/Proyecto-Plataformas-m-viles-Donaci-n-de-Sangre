package com.uvg.gotavida.ui.vistaadmin.solicitud.cerrada_asignada

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CerradaAsignadaViewModel : ViewModel() {

    private val _state = MutableStateFlow(CerradaAsignadaState(isLoading = true))
    val state: StateFlow<CerradaAsignadaState> = _state.asStateFlow()

    init {
        cargarPantalla()
    }

    fun onEvent(event: CerradaAsignadaEvent) {
        when (event) {
            // Solo navega al dashboard; no cambia el estado.
            CerradaAsignadaEvent.BackClick -> Unit
        }
    }

    private fun cargarPantalla() {
        viewModelScope.launch {
            delay(2000)
            _state.update { it.copy(isLoading = false) }
        }
    }
}
