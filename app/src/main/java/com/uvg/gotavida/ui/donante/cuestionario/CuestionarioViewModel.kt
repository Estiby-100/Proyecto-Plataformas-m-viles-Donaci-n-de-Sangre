package com.uvg.gotavida.ui.donante.cuestionario

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uvg.gotavida.data.fake.FakePreguntasDataSource
import com.uvg.gotavida.data.model.RespuestaElegibilidad

class CuestionarioViewModel : ViewModel() {

    var state by mutableStateOf(
        CuestionarioState(
            pregunta = FakePreguntasDataSource.obtenerPregunta(1)
        )
    )
        private set

    fun onEvent(event: CuestionarioEvent) {

        when (event) {

            is CuestionarioEvent.Respuesta -> {

                state = state.copy(
                    respuestaSeleccionada = event.respuesta
                )
            }

            CuestionarioEvent.Siguiente -> {

                val preguntaActual = state.pregunta

                if (state.numeroPregunta < preguntaActual.total) {

                    val siguiente = state.numeroPregunta + 1

                    state = state.copy(
                        numeroPregunta = siguiente,
                        pregunta = FakePreguntasDataSource
                            .obtenerPregunta(siguiente),
                        respuestaSeleccionada = null
                    )

                } else {

                    state = state.copy(
                        vista = VistaCuestionario.RESULTADO_APTO
                    )
                }
            }

            CuestionarioEvent.Mostrar -> {

                state = state.copy(
                    mostrarDialogoContacto = true
                )
            }

            CuestionarioEvent.Ocultar -> {

                state = state.copy(
                    mostrarDialogoContacto = false
                )
            }

            CuestionarioEvent.Continuar -> Unit
            CuestionarioEvent.Inicio -> Unit
            CuestionarioEvent.Cerrar -> Unit
        }
    }
}