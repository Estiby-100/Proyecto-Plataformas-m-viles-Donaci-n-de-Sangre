package com.uvg.gotavida.ui.vistaadmin.user.perfil

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PerfilViewModel : ViewModel() {

    var state by mutableStateOf(
        PerfilState()
    )
        private set

    fun onEvent(
        event: PerfilEvent
    ) {

        when (event) {

            PerfilEvent.BackClick -> {
                // navegación
            }

            PerfilEvent.CerrarSesion -> {
                // navegacion
            }
        }
    }
}