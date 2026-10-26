package com.uvg.gotavida.data.model

/** Solicitud publicada por una cuenta médica (vista de admin). */
data class SolicitudCreada(
    val nombre: String,
    val ubicacion: String,
    val tipoSangre: String
)
