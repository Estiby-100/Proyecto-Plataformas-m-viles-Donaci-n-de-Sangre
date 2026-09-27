package com.uvg.gotavida.data.model

enum class EstadoHistorial {
    COMPLETADA,
    CANCELADA
}

data class ItemHistorial(
    val id: String,
    val institucion: String,
    val fechaTexto: String,
    val estado: EstadoHistorial
)

data class Insignia(
    val id: String,
    val nombre: String,
    val desbloqueada: Boolean
)
