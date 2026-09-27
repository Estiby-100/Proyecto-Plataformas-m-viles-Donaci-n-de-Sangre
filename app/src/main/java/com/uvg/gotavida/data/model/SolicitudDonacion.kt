package com.uvg.gotavida.data.model

enum class NivelUrgencia {
    URGENTE,
    PROGRAMADA
}

enum class TipoPublicacion {
    SOLICITUD_SANGRE,
    JORNADA
}

data class SolicitudDonacion(
    val id: String,
    val institucion: String,
    val institucionVerificada: Boolean,
    val zona: String,
    val tipoSangre: String,
    val tipoPublicacion: TipoPublicacion,
    val urgencia: NivelUrgencia,
    val distanciaKm: Double,
    val vigenciaTexto: String,
    val direccion: String,
    val horario: String,
    val telefonoInstitucion: String,
    val estaCerrada: Boolean = false
)
