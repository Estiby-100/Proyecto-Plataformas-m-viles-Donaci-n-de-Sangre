package com.uvg.gotavida.data.fake

import com.uvg.gotavida.data.model.EstadoHistorial
import com.uvg.gotavida.data.model.Insignia
import com.uvg.gotavida.data.model.ItemHistorial

object FakePerfilDataSource {

    fun obtenerHistorial(): List<ItemHistorial> = listOf(
        ItemHistorial(
            id = "hist-001",
            institucion = "Hospital Roosevelt",
            fechaTexto = "12 ago",
            estado = EstadoHistorial.COMPLETADA
        ),
        ItemHistorial(
            id = "hist-002",
            institucion = "Cruz Roja",
            fechaTexto = "3 jun",
            estado = EstadoHistorial.COMPLETADA
        ),
        ItemHistorial(
            id = "hist-003",
            institucion = "Centro Médico Militar",
            fechaTexto = "15 mar",
            estado = EstadoHistorial.CANCELADA
        )
    )

    fun obtenerInsignias(): List<Insignia> = listOf(
        Insignia(id = "ins-001", nombre = "Primera donación", desbloqueada = true),
        Insignia(id = "ins-002", nombre = "3 donaciones", desbloqueada = true),
        Insignia(id = "ins-003", nombre = "Donante frecuente", desbloqueada = false)
    )
}
