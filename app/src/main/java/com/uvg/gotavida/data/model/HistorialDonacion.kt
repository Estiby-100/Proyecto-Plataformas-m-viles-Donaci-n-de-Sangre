package com.uvg.gotavida.data.model

enum class EstadoHistorial {
    COMPLETADA,
    CANCELADA
}

/**
 * Un ítem del historial que se muestra en el Perfil del donante (pantalla 11).
 * No es interactivo en esta entrega (no abre un detalle propio).
 */
data class ItemHistorial(
    val id: String,
    val institucion: String,
    val fechaTexto: String,
    val estado: EstadoHistorial
)

/**
 * Insignia de la sección "Insignias" del Perfil. Es una recomendación de UX
 * adicional, no un requisito confirmado en los documentos del proyecto.
 */
data class Insignia(
    val id: String,
    val nombre: String,
    val desbloqueada: Boolean
)
