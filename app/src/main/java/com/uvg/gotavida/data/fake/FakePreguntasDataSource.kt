package com.uvg.gotavida.data.fake

import com.uvg.gotavida.data.model.PreguntaElegibilidad

/**
 * Fuente de datos "fake" del cuestionario de orientación (pantalla 8).
 *
 * CONTENIDO DEMOSTRATIVO PENDIENTE DE VALIDACIÓN MÉDICA: los documentos del
 * proyecto no incluyen criterios médicos oficiales de elegibilidad para donar
 * sangre. Estas cinco preguntas son solo un ejemplo de formato (una pregunta a
 * la vez, con progreso) y deben confirmarse con una fuente médica oficial
 * (ej. Cruz Roja Guatemala / MSPAS) antes de usarse en producción.
 */
object FakePreguntasDataSource {

    private val preguntas = listOf(
        PreguntaElegibilidad(
            id = 1,
            numero = 1,
            total = 5,
            texto = "¿Has donado sangre en los últimos meses?",
            ayuda = "Pendiente de validación médica: el número exacto de días de espera no está definido."
        ),
        PreguntaElegibilidad(
            id = 2,
            numero = 2,
            total = 5,
            texto = "¿Te sientes bien de salud hoy?"
        ),
        PreguntaElegibilidad(
            id = 3,
            numero = 3,
            total = 5,
            texto = "¿Pesas más de cierto mínimo?",
            ayuda = "Pendiente de validación médica: el mínimo exacto no está definido."
        ),
        PreguntaElegibilidad(
            id = 4,
            numero = 4,
            total = 5,
            texto = "¿Tienes alguna condición médica que te haya indicado no donar?"
        ),
        PreguntaElegibilidad(
            id = 5,
            numero = 5,
            total = 5,
            texto = "¿Tomaste algún medicamento en los últimos días?",
            ayuda = "Pendiente de validación médica: la lista de medicamentos aplicables no está definida."
        )
    )

    fun obtenerPreguntas(): List<PreguntaElegibilidad> = preguntas

    fun obtenerPregunta(numero: Int): PreguntaElegibilidad = preguntas[numero - 1]
}
