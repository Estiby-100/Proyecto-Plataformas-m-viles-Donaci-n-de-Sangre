package com.uvg.gotavida.ui.donante.perfil

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakePerfilDataSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class PerfilViewModel : ViewModel() {

    var state by mutableStateOf(
        PerfilState(
            historial = FakePerfilDataSource.obtenerHistorial(),
            insignias = FakePerfilDataSource.obtenerInsignias()
        )
    )
        private set

    fun onEvent(event: PerfilEvent) {

        when (event) {

            is PerfilEvent.Disponibilidad -> {

                state = state.copy(
                    disponible = event.disponible
                )
            }
        }
    }
}
