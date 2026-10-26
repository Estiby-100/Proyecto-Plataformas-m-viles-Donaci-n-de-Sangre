package com.uvg.gotavida.ui.vistaadmin.user.perfil

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerfilViewModel : ViewModel() {

    private val _state = MutableStateFlow(PerfilState())
    val state: StateFlow<PerfilState> = _state.asStateFlow()

    fun onEvent(
        event: PerfilEvent
    ) {

        when (event) {

            // El repositorio se pide aquí y no en el constructor para que el
            // @Preview del perfil no necesite Firebase ni Room.
            PerfilEvent.CerrarSesion -> ServiceLocator.cuentaRepository.cerrarSesion()

            // Solo navegan; no cambian el estado.
            PerfilEvent.BackClick -> Unit
            PerfilEvent.Ajustes -> Unit
        }
    }
}
