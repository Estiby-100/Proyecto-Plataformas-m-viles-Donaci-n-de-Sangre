package com.uvg.gotavida.ui.donante.cuestionario

import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakePreguntasDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CuestionarioViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        CuestionarioState(
            pregunta = FakePreguntasDataSource.obtenerPregunta(1)
        )
    )
    val state: StateFlow<CuestionarioState> = _state.asStateFlow()

    fun onEvent(event: CuestionarioEvent) {

        when (event) {

            is CuestionarioEvent.Respuesta -> {
                _state.update { it.copy(respuestaSeleccionada = event.respuesta) }
            }

            CuestionarioEvent.Siguiente -> {
                _state.update { actual ->
                    if (actual.numeroPregunta < actual.pregunta.total) {
                        val siguiente = actual.numeroPregunta + 1
                        actual.copy(
                            numeroPregunta = siguiente,
                            pregunta = FakePreguntasDataSource.obtenerPregunta(siguiente),
                            respuestaSeleccionada = null
                        )
                    } else {
                        actual.copy(vista = VistaCuestionario.RESULTADO_APTO)
                    }
                }
            }

            CuestionarioEvent.Mostrar -> {
                _state.update { it.copy(mostrarDialogoContacto = true) }
            }

            CuestionarioEvent.Ocultar -> {
                _state.update { it.copy(mostrarDialogoContacto = false) }
            }

            // Solo navegan; no cambian el estado.
            CuestionarioEvent.Continuar -> Unit
            CuestionarioEvent.Inicio -> Unit
            CuestionarioEvent.Cerrar -> Unit
        }
    }
}
