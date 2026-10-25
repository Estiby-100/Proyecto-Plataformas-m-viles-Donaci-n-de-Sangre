package com.uvg.gotavida.ui.shared.notificaciones

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakeNotificacionesDataSource
import com.uvg.gotavida.data.model.RolCuenta

class NotificacionesViewModel : ViewModel() {

    var state by mutableStateOf(
        NotificacionesUiState()
    )
        private set

    init {
        onEvent(
            NotificacionesEvent.Cargar
        )
    }

    fun onEvent(
        event: NotificacionesEvent
    ) {

        when (event) {

            NotificacionesEvent.Cargar -> {

                state = state.copy(
                    cargando = false,
                    rol = RolCuenta.DONANTE,
                    notificaciones = FakeNotificacionesDataSource.donante
                )
            }

            NotificacionesEvent.Marcar -> {

                state = state.copy(
                    notificaciones = state.notificaciones.map {
                        it.copy(leida = true)
                    }
                )
            }

            is NotificacionesEvent.Abrir -> {

                state = state.copy(
                    notificaciones = state.notificaciones.map {

                        if (it.id == event.notificacion.id) {
                            it.copy(leida = true)
                        } else {
                            it
                        }
                    }
                )
            }

            NotificacionesEvent.BackClick -> {
                // navegacion
            }
        }
    }
}