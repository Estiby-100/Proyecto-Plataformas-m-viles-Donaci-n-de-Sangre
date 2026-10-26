package com.uvg.gotavida.ui.donante.perfil

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakePerfilDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PerfilViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        PerfilState(
            historial = FakePerfilDataSource.obtenerHistorial(),
            insignias = FakePerfilDataSource.obtenerInsignias()
        )
    )
    val state: StateFlow<PerfilState> = _state.asStateFlow()

    fun onEvent(event: PerfilEvent) {

        when (event) {

            is PerfilEvent.Disponibilidad -> {
                _state.update { it.copy(disponible = event.disponible) }
            }

            // Solo navega; no cambia el estado.
            PerfilEvent.Ajustes -> Unit
        }
    }
}
