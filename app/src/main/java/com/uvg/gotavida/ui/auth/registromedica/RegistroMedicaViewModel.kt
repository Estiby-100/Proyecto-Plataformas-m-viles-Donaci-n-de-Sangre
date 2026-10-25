package com.uvg.gotavida.ui.auth.registromedica

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

class RegistroMedicaViewModel : ViewModel() {

    var state by mutableStateOf(
        RegistroMedicaUiState()
    )
        private set

    fun onEvent(event: RegistroMedicaEvent) {

        when (event) {

            is RegistroMedicaEvent.Institucion -> {

                state = state.copy(
                    nombreInstitucion = event.nombre,
                    errorMessage = null
                )
            }

            is RegistroMedicaEvent.Correo -> {

                state = state.copy(
                    correoInstitucional = event.correo,
                    errorMessage = null
                )
            }

            is RegistroMedicaEvent.NumeroColegiado -> {

                state = state.copy(
                    numeroColegiado = event.numero
                )
            }

            RegistroMedicaEvent.Enviar -> {

                state = state.copy(
                    fase = RegistroMedicaFase.EN_REVISION
                )
            }

            RegistroMedicaEvent.Back -> Unit

            RegistroMedicaEvent.Inicio -> Unit
        }
    }
}