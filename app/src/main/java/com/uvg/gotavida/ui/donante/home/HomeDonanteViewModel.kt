package com.uvg.gotavida.ui.donante.home

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource

class HomeDonanteViewModel : ViewModel() {

    var state by mutableStateOf(
        HomeDonanteState()
    )
        private set

    init {
        cargarFeed()
    }

    fun onEvent(event: HomeDonanteEvent) {

        when (event) {

            is HomeDonanteEvent.Disponibilidad -> {

                state = state.copy(
                    disponible = event.disponible
                )
            }

            is HomeDonanteEvent.Filtro -> {

                state = state.copy(
                    filtroSeleccionado = event.filtro
                )

                cargarFeed()
            }

            HomeDonanteEvent.Cargar -> {
                cargarFeed()
            }

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

        val solicitudesAMostrar =
            when (state.filtroSeleccionado) {

                FiltroFeed.JORNADAS -> emptyList()

                else -> solicitudes
            }

        val jornadasAMostrar =
            when (state.filtroSeleccionado) {

                FiltroFeed.SOLICITUDES -> emptyList()

                else -> jornadas
            }

        val estado =
            if (
                solicitudesAMostrar.isEmpty()
                && jornadasAMostrar.isEmpty()
            ) {
                EstadoFeed.VACIO
            } else {
                EstadoFeed.CON_CONTENIDO
            }

        state = state.copy(
            estado = estado,
            urgentes = solicitudesAMostrar,
            jornadas = jornadasAMostrar
        )
    }
}