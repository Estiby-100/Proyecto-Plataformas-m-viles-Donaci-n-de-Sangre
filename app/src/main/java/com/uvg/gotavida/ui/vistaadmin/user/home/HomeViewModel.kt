package com.uvg.gotavida.ui.vistaadmin.user.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    var state by mutableStateOf(
        HomeState()
    )
        private set

    fun onEvent(
        event: HomeEvent
    ) {

        when (event) {

            HomeEvent.CrearSolicitud -> {
                // Navegacion a implementar
            }

            HomeEvent.VerSolicitudes -> {
                // Navegación a imprlementar
            }
        }
    }
}