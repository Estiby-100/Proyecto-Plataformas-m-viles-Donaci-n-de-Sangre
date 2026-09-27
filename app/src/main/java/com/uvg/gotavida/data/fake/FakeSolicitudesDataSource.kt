package com.uvg.gotavida.data.fake

import com.uvg.gotavida.data.model.NivelUrgencia
import com.uvg.gotavida.data.model.SolicitudDonacion
import com.uvg.gotavida.data.model.TipoPublicacion

object FakeSolicitudesDataSource {

    fun obtenerSolicitudesUrgentesCompatibles(): List<SolicitudDonacion> = listOf(
        SolicitudDonacion(
            id = "sol-001",
            institucion = "Hospital Roosevelt",
            institucionVerificada = true,
            zona = "Zona 11",
            tipoSangre = "O+",
            tipoPublicacion = TipoPublicacion.SOLICITUD_SANGRE,
            urgencia = NivelUrgencia.URGENTE,
            distanciaKm = 2.3,
            vigenciaTexto = "Vence hoy, 6:00 pm",
            direccion = "6ta avenida, Zona 11",
            horario = "7:00 am - 4:00 pm",
            telefonoInstitucion = "0000-0000"
        ),
        SolicitudDonacion(
            id = "sol-002",
            institucion = "Centro Médico Militar",
            institucionVerificada = true,
            zona = "Zona 5",
            tipoSangre = "A+",
            tipoPublicacion = TipoPublicacion.SOLICITUD_SANGRE,
            urgencia = NivelUrgencia.URGENTE,
            distanciaKm = 4.1,
            vigenciaTexto = "Vence mañana, 9:00 am",
            direccion = "Avenida las Américas, Zona 5",
            horario = "8:00 am - 5:00 pm",
            telefonoInstitucion = "0000-0000"
        )
    )

    fun obtenerJornadasCercanas(): List<SolicitudDonacion> = listOf(
        SolicitudDonacion(
            id = "jor-001",
            institucion = "Cruz Roja Guatemala",
            institucionVerificada = true,
            zona = "Zona 10",
            tipoSangre = "Todos los tipos",
            tipoPublicacion = TipoPublicacion.JORNADA,
            urgencia = NivelUrgencia.PROGRAMADA,
            distanciaKm = 3.5,
            vigenciaTexto = "Sáb 12 sep, 9:00 am - 1:00 pm",
            direccion = "13 calle, Zona 10",
            horario = "9:00 am - 1:00 pm",
            telefonoInstitucion = "0000-0000"
        )
    )

    fun obtenerSolicitudPorId(id: String): SolicitudDonacion? =
        (obtenerSolicitudesUrgentesCompatibles() + obtenerJornadasCercanas())
            .firstOrNull { it.id == id }

    fun obtenerSolicitudCerradaDeEjemplo(): SolicitudDonacion =
        obtenerSolicitudesUrgentesCompatibles().first().copy(
            id = "sol-003",
            vigenciaTexto = "Venció ayer, 6:00 pm",
            estaCerrada = true
        )
}
