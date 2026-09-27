package com.uvg.gotavida.data.model

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
