package com.uvg.gotavida.data.model

/**
 * Pregunta del cuestionario de orientación (pantalla 8).
 *
 * IMPORTANTE: el contenido de estas preguntas es demostrativo. Los documentos
 * del proyecto no incluyen criterios médicos oficiales de elegibilidad para
 * donar sangre, así que ninguna pregunta aquí debe tratarse como un criterio
 * clínico real; ver notas en FakePreguntasDataSource.
 */
data class PreguntaElegibilidad(
    val id: Int,
    val numero: Int,
    val total: Int,
    val texto: String,
    val ayuda: String? = null
)

enum class RespuestaElegibilidad {
    SI,
    NO
}

enum class ResultadoElegibilidad {
    POSIBLEMENTE_APTO,
    POSIBLEMENTE_NO_APTO
}
