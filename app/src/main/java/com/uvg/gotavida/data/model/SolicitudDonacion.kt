package com.uvg.gotavida.data.model

/**
 * Nivel de urgencia de una publicación. Se usa tanto en el feed de Home
 * como en el Detalle y la Confirmación.
 */
enum class NivelUrgencia {
    URGENTE,
    PROGRAMADA
}

/**
 * Diferencia una solicitud puntual de sangre de una jornada o campaña
 * institucional (requisito: el feed debe distinguir ambos tipos con claridad).
 */
enum class TipoPublicacion {
    SOLICITUD_SANGRE,
    JORNADA
}

/**
 * Una solicitud de sangre o jornada publicada por una institución, tal como
 * aparece en el feed de Home, el Detalle y la Confirmación (pantallas 6, 7 y 9).
 *
 * Todos los textos de fecha/horario llegan ya formateados desde la fuente de
 * datos (fake por ahora) porque esta fase es solo de vistas: no hay lógica de
 * fechas real todavía.
 */
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
