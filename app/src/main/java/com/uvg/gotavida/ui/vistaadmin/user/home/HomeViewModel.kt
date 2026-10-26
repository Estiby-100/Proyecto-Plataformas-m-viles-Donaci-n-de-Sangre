package com.uvg.gotavida.ui.vistaadmin.user.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun onEvent(
        event: HomeEvent
    ) {

        when (event) {
            // Todas estas acciones solo navegan; no cambian el estado.
            HomeEvent.CrearSolicitud,
            HomeEvent.VerSolicitudes,
            HomeEvent.Perfil,
            HomeEvent.Notificaciones -> Unit
        }
    }
}
