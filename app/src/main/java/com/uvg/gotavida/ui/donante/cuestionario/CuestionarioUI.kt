package com.uvg.gotavida.ui.donante.cuestionario

import com.uvg.gotavida.data.model.PreguntaElegibilidad
import com.uvg.gotavida.data.model.RespuestaElegibilidad

data class CuestionarioState(
    val numeroPregunta: Int = 1,
    val pregunta: PreguntaElegibilidad,
    val respuestaSeleccionada: RespuestaElegibilidad? = null,
    val vista: VistaCuestionario = VistaCuestionario.PREGUNTA,
    val mostrarDialogoContacto: Boolean = false
)

sealed interface CuestionarioEvent {
    data class Respuesta(
        val respuesta: RespuestaElegibilidad
    ) : CuestionarioEvent
    data object Siguiente : CuestionarioEvent
    data object Mostrar : CuestionarioEvent
    data object Ocultar : CuestionarioEvent
    data object Continuar : CuestionarioEvent
    data object Inicio : CuestionarioEvent
    data object Cerrar : CuestionarioEvent
}