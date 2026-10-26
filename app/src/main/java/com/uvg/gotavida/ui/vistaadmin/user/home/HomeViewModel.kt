package com.uvg.gotavida.ui.vistaadmin.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.fake.SolicitudesCreadasStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        // El contador del dashboard sigue a las solicitudes activas.
        viewModelScope.launch {
            SolicitudesCreadasStore.solicitudes.collect { lista ->
                _state.update { it.copy(requestCount = lista.size) }
            }
        }
    }

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
