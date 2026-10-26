package com.uvg.gotavida.ui.donante.mapa

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakeLugaresDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapaViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        MapaState(
            lugares = FakeLugaresDataSource.obtenerLugaresCercanos()
        )
    )
    val state: StateFlow<MapaState> = _state.asStateFlow()

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
