package com.uvg.gotavida.data.fake

import com.uvg.gotavida.data.model.LugarMapa
import com.uvg.gotavida.data.model.TipoPublicacion

object FakeLugaresDataSource {

    fun obtenerLugaresCercanos(): List<LugarMapa> = listOf(
        LugarMapa(
            id = "lugar-001",
            nombre = "Hospital Roosevelt",
            direccion = "6ta avenida, Zona 11",
            distanciaKm = 2.3,
            horarioTexto = "Abierto ahora - Cierra 4:00 pm",
            abiertoAhora = true,
            tipo = TipoPublicacion.SOLICITUD_SANGRE,
            xRelativo = 0.30f,
            yRelativo = 0.35f,
            tieneSolicitudesActivas = true
        ),
        LugarMapa(
            id = "lugar-002",
            nombre = "Centro Médico Militar",
            direccion = "Avenida las Américas, Zona 5",
            distanciaKm = 4.1,
            horarioTexto = "Abierto ahora - Cierra 5:00 pm",
            abiertoAhora = true,
            tipo = TipoPublicacion.SOLICITUD_SANGRE,
            xRelativo = 0.68f,
            yRelativo = 0.22f,
            tieneSolicitudesActivas = true
        ),
        LugarMapa(
            id = "lugar-003",
            nombre = "Cruz Roja Guatemala",
            direccion = "13 calle, Zona 10",
            distanciaKm = 3.5,
            horarioTexto = "Jornada: sáb 9:00 am - 1:00 pm",
            abiertoAhora = false,
            tipo = TipoPublicacion.JORNADA,
            xRelativo = 0.45f,
            yRelativo = 0.62f,
            tieneSolicitudesActivas = false
        ),
        LugarMapa(
            id = "lugar-004",
            nombre = "Banco de Sangre Nacional",
            direccion = "9na calle, Zona 1",
            distanciaKm = 5.8,
            horarioTexto = "Abierto ahora - Cierra 6:00 pm",
            abiertoAhora = true,
            tipo = TipoPublicacion.JORNADA,
            xRelativo = 0.75f,
            yRelativo = 0.72f,
            tieneSolicitudesActivas = false
        )
    )
}
