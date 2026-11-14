package com.uvg.gotavida.ui.donante.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.uvg.gotavida.di.ServiceLocator

class HomeDonanteViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeDonanteState())
    val state: StateFlow<HomeDonanteState> = _state.asStateFlow()

    init {
        cargarFeed()
        cargarNombre()
    }

    /**
     * Lee el perfil guardado en Room para saludar con el nombre real.
     * runCatching evita que el @Preview falle, porque ahí no existe Room.
     */
    private fun cargarNombre() {
        viewModelScope.launch {
            val usuario = runCatching { ServiceLocator.cuentaRepository.sesionActual() }.getOrNull()
            if (usuario != null) {
                _state.update { it.copy(nombre = usuario.nombre) }
            }
        }
    }

    fun onEvent(event: HomeDonanteEvent) {

        when (event) {

            is HomeDonanteEvent.Disponibilidad -> {
                _state.update { it.copy(disponible = event.disponible) }
            }

            is HomeDonanteEvent.Filtro -> {
                _state.update { it.copy(filtroSeleccionado = event.filtro) }
                cargarFeed()
            }

            HomeDonanteEvent.Cargar -> cargarFeed()

            // Solo navegan; no cambian el estado.
            HomeDonanteEvent.Notificaciones -> Unit
            is HomeDonanteEvent.Detalle -> Unit
        }
    }

    private fun cargarFeed() {

        val solicitudes =
            FakeSolicitudesDataSource
                .obtenerSolicitudesUrgentesCompatibles()

        val jornadas =
            FakeSolicitudesDataSource
                .obtenerJornadasCercanas()

        _state.update { actual ->

            val solicitudesAMostrar =
                when (actual.filtroSeleccionado) {
                    FiltroFeed.JORNADAS -> emptyList()
                    else -> solicitudes
                }

            val jornadasAMostrar =
                when (actual.filtroSeleccionado) {
                    FiltroFeed.SOLICITUDES -> emptyList()
                    else -> jornadas
                }

            val estado =
                if (solicitudesAMostrar.isEmpty() && jornadasAMostrar.isEmpty()) {
                    EstadoFeed.VACIO
                } else {
                    EstadoFeed.CON_CONTENIDO
                }

            actual.copy(
                estado = estado,
                urgentes = solicitudesAMostrar,
                jornadas = jornadasAMostrar
            )
        }
    }
}
