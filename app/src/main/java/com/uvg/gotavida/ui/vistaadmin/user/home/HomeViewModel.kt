package com.uvg.gotavida.ui.vistaadmin.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        // Nombre real de la médica, leído de Room. runCatching protege el @Preview.
        viewModelScope.launch {
            val usuario = runCatching { ServiceLocator.cuentaRepository.sesionActual() }.getOrNull()
            if (usuario != null) {
                _state.update { it.copy(userName = usuario.nombre) }
            }
        }

        // El contador del dashboard sigue a las solicitudes activas de esta médica.
        viewModelScope.launch {
            val uid = runCatching { ServiceLocator.authRepository.uidActual }.getOrNull()
            val repositorio = runCatching { ServiceLocator.solicitudesRepository }.getOrNull()
            if (uid != null && repositorio != null) {
                repositorio.observarDeMedica(uid)
                    .catch { }
                    .collect { lista -> _state.update { it.copy(requestCount = lista.size) } }
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
