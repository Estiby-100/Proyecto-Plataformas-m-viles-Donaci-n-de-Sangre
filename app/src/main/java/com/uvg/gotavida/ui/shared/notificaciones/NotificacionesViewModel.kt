package com.uvg.gotavida.ui.shared.notificaciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.fake.NotificacionesStore
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificacionesViewModel : ViewModel() {

    private val _state = MutableStateFlow(NotificacionesState())
    val state: StateFlow<NotificacionesState> = _state.asStateFlow()

    private var carga: Job? = null

    fun onEvent(
        event: NotificacionesEvent
    ) {

        when (event) {

            is NotificacionesEvent.Cargar -> {
                carga?.cancel()
                carga = viewModelScope.launch {
                    NotificacionesStore.de(event.rol).collect { lista ->
                        _state.update {
                            it.copy(cargando = false, rol = event.rol, notificaciones = lista)
                        }
                    }
                }
            }

            NotificacionesEvent.Marcar -> {
                NotificacionesStore.marcarTodas(_state.value.rol)
            }

            is NotificacionesEvent.Abrir -> {
                NotificacionesStore.marcarLeida(_state.value.rol, event.notificacionId)
            }

            NotificacionesEvent.BackClick -> Unit
        }
    }
}
