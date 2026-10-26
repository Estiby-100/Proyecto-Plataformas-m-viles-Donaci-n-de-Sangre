package com.uvg.gotavida.ui.shared.notificaciones

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakeNotificacionesDataSource
import com.uvg.gotavida.data.model.RolCuenta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotificacionesViewModel : ViewModel() {

    private val _state = MutableStateFlow(NotificacionesState())
    val state: StateFlow<NotificacionesState> = _state.asStateFlow()

    fun onEvent(
        event: NotificacionesEvent
    ) {

        when (event) {

            is NotificacionesEvent.Cargar -> {
                val notificaciones = when (event.rol) {
                    RolCuenta.DONANTE -> FakeNotificacionesDataSource.donante
                    RolCuenta.MEDICA -> FakeNotificacionesDataSource.medica
                }
                _state.update {
                    it.copy(
                        cargando = false,
                        rol = event.rol,
                        notificaciones = notificaciones
                    )
                }
            }

            NotificacionesEvent.Marcar -> {
                _state.update {
                    it.copy(notificaciones = it.notificaciones.map { n -> n.copy(leida = true) })
                }
            }

            is NotificacionesEvent.Abrir -> {
                _state.update {
                    it.copy(
                        notificaciones = it.notificaciones.map { n ->
                            if (n.id == event.notificacionId) n.copy(leida = true) else n
                        }
                    )
                }
            }

            NotificacionesEvent.BackClick -> Unit
        }
    }
}
