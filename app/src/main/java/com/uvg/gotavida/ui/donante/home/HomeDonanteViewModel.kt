package com.uvg.gotavida.ui.donante.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.fake.FakeSolicitudesDataSource
import com.uvg.gotavida.data.model.SolicitudDonacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.uvg.gotavida.di.ServiceLocator

class HomeDonanteViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeDonanteState())
    val state: StateFlow<HomeDonanteState> = _state.asStateFlow()

    init {
        observarSolicitudes()
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

    /** Solicitudes activas que llegan de Firestore; se actualizan solas cuando una médica publica. */
    private var solicitudesRemotas: List<SolicitudDonacion> = emptyList()

    /**
     * Escucha las solicitudes de Firestore. El repositorio se pide aquí y con
     * runCatching para que el @Preview no necesite Firebase. Las jornadas siguen
     * siendo datos de ejemplo del equipo.
     */
    private fun observarSolicitudes() {
        viewModelScope.launch {
            val repositorio = runCatching { ServiceLocator.solicitudesRepository }.getOrNull()
            if (repositorio == null) {
                cargarFeed()
                return@launch
            }
            repositorio.observarActivas()
                .catch { _state.update { it.copy(estado = EstadoFeed.ERROR) } }
                .collect { lista ->
                    solicitudesRemotas = lista
                    cargarFeed()
                }
        }
    }

    private fun cargarFeed() {

        val solicitudes = solicitudesRemotas

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
