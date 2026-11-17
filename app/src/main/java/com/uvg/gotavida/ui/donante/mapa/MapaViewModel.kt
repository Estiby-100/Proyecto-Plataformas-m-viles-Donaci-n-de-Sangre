package com.uvg.gotavida.ui.donante.mapa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.data.fake.FakeLugaresDataSource
import com.uvg.gotavida.data.model.LugarMapa
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapaViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        MapaState(
            lugares = FakeLugaresDataSource.obtenerLugaresCercanos()
        )
    )
    val state: StateFlow<MapaState> = _state.asStateFlow()

    init {
        observarSolicitudes()
    }

    private fun observarSolicitudes() {
        viewModelScope.launch {
            val repo = runCatching { ServiceLocator.solicitudesRepository }.getOrNull() ?: return@launch
            repo.observarActivas().collect { solicitudes ->
                if (solicitudes.isNotEmpty()) {
                    val lugaresReales = solicitudes.mapIndexed { index, sol ->
                        val (x, y) = posicionPorZona(sol.zona, index)
                        LugarMapa(
                            id = sol.id,
                            nombre = sol.institucion,
                            direccion = sol.direccion.ifBlank { "Zona ${sol.zona}" },
                            distanciaKm = sol.distanciaKm,
                            horarioTexto = sol.horario,
                            abiertoAhora = true,
                            tipo = sol.tipoPublicacion,
                            xRelativo = x,
                            yRelativo = y,
                            tieneSolicitudesActivas = true
                        )
                    }
                    _state.update { it.copy(lugares = lugaresReales + FakeLugaresDataSource.obtenerLugaresCercanos().filter { f -> f.tipo == com.uvg.gotavida.data.model.TipoPublicacion.JORNADA }) }
                }
            }
        }
    }

    private fun posicionPorZona(zona: String, index: Int): Pair<Float, Float> {
        val z = zona.lowercase()
        return when {
            z.contains("10") -> Pair(0.45f, 0.62f)
            z.contains("11") -> Pair(0.30f, 0.35f)
            z.contains("5") -> Pair(0.68f, 0.22f)
            z.contains("1") -> Pair(0.75f, 0.72f)
            else -> Pair(0.25f + (index * 0.18f) % 0.55f, 0.25f + (index * 0.22f) % 0.55f)
        }
    }

    fun onEvent(event: MapaEvent) {

        when (event) {

            MapaEvent.Vista -> {
                _state.update { it.copy(vista = !it.vista) }
            }

            is MapaEvent.Lugar -> {
                _state.update { it.copy(seleccionado = event.lugar) }
            }

            MapaEvent.Cerrar -> {
                _state.update { it.copy(seleccionado = null) }
            }

            MapaEvent.Ubicacion -> Unit
        }
    }
}
