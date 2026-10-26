package com.uvg.gotavida.data.model

data class PreguntaElegibilidad(
    val id: Int,
    val numero: Int,
    val total: Int,
    val texto: String,
    val ayuda: String? = null,
    /** Respuesta que hace que la persona no pueda donar por ahora. */
    val respuestaNoApta: RespuestaElegibilidad = RespuestaElegibilidad.SI
)

enum class RespuestaElegibilidad {
    SI,
    NO
}

enum class ResultadoElegibilidad {
    POSIBLEMENTE_APTO,
    POSIBLEMENTE_NO_APTO
}
