package com.uvg.gotavida.data.model

/** Solicitud publicada por una cuenta médica (vista de admin). */
data class SolicitudCreada(
    /** Id del documento en Firestore; vacío si la solicitud aún no se guardó. */
    val id: String = "",
    val nombre: String,
    val ubicacion: String,
    val tipoSangre: String
)
