package com.uvg.gotavida.ui.donante.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.fake.FakePerfilDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.uvg.gotavida.di.ServiceLocator

class PerfilViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        PerfilState(
            historial = FakePerfilDataSource.obtenerHistorial(),
            insignias = FakePerfilDataSource.obtenerInsignias()
        )
    )
    val state: StateFlow<PerfilState> = _state.asStateFlow()

    init {
        cargarPerfil()
    }

    /** Nombre y tipo de sangre reales, leídos de Room (el perfil que se guardó al registrarse). */
    private fun cargarPerfil() {
        viewModelScope.launch {
            val usuario = runCatching { ServiceLocator.cuentaRepository.sesionActual() }.getOrNull()
            if (usuario != null) {
                _state.update {
                    it.copy(
                        nombre = usuario.nombre,
                        sangre = usuario.tipoSangre?.etiqueta ?: "—"
                    )
                }
            }
        }
    }

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
