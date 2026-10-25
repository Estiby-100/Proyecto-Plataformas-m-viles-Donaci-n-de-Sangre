package com.uvg.gotavida.ui.auth.registrodonante

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class RegistroDonanteViewModel : ViewModel() {

    var state by mutableStateOf(
        RegistroDonanteUiState()
    )
        private set

    fun onEvent(event: RegistroDonanteEvent) {

        when (event) {

            is RegistroDonanteEvent.BloodTypeSelected -> {

                state = state.copy(
                    selectedBloodType = event.tipo
                )
            }

            is RegistroDonanteEvent.Ciudad -> {

                state = state.copy(
                    ciudadOZona = event.ciudad,
                    errorMessage = null
                )
            }

            RegistroDonanteEvent.Continue -> {
                //navegacion
            }

            RegistroDonanteEvent.Back -> {
                //navegacion
            }
        }
    }
}