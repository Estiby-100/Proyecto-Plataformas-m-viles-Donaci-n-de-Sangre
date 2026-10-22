package com.uvg.gotavida.ui.vistaadmin.solicitud.cerrada_asignada

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CerradaAsignadaViewModel : ViewModel() {

    var state by mutableStateOf(
        CerradaAsignadaState(
            isLoading = true
        )
    )
        private set

    init {
        cargarPantalla()
    }

    fun onEvent(event: CerradaAsignadaEvent) {
        when (event) {
            CerradaAsignadaEvent.BackClick -> {

            }
        }
    }

    private fun cargarPantalla() {
        viewModelScope.launch {

            delay(2000)

            state = state.copy(
                isLoading = false
            )
        }
    }
}