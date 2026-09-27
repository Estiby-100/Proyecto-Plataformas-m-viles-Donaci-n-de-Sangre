package com.uvg.gotavida.data.model

/**
 * Un lugar mostrado en el Mapa (pantalla 10): puede ser un banco de sangre
 * con una solicitud urgente activa, o una jornada/campaña regular.
 *
 * Las coordenadas son relativas (0f a 1f) dentro del área del mapa dibujado
 * en pantalla; esta fase no integra un SDK de mapas real, solo la vista.
 */
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
