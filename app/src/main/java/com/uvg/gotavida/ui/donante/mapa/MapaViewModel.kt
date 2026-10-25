package com.uvg.gotavida.ui.donante.mapa

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakeLugaresDataSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MapaViewModel : ViewModel() {

    var state by mutableStateOf(
        MapaState(
            lugares =
                FakeLugaresDataSource
                    .obtenerLugaresCercanos()
        )
    )
        private set

    fun onEvent(event: MapaEvent) {

        when (event) {

            MapaEvent.Vista -> {

                state = state.copy(
                    vista = !state.vista
                )
            }

            is MapaEvent.Lugar -> {

                state = state.copy(
                    seleccionado = event.lugar
                )
            }

            MapaEvent.Cerrar -> {

                state = state.copy(
                    seleccionado = null
                )
            }

            MapaEvent.Ubicacion -> Unit
        }
    }
}