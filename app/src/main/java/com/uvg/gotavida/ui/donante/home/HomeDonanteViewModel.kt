package com.uvg.gotavida.ui.donante.home

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeDonanteViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeDonanteState())
    val state: StateFlow<HomeDonanteState> = _state.asStateFlow()

    init {
        cargarFeed()
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
