package com.uvg.gotavida.data.model

data class LugarMapa(
    val id: String,
    val nombre: String,
    val direccion: String,
    val distanciaKm: Double,
    val horarioTexto: String,
    val abiertoAhora: Boolean,
    val tipo: TipoPublicacion,
    val xRelativo: Float,
    val yRelativo: Float,
    val tieneSolicitudesActivas: Boolean
)
