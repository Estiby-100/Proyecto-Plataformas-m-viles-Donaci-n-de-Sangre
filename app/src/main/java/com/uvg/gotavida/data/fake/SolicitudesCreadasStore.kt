package com.uvg.gotavida.data.fake

import com.uvg.gotavida.data.model.SolicitudCreada
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Solicitudes activas de la cuenta médica, en memoria. Las comparten el
 * dashboard, Nueva solicitud y Solicitudes creadas, así lo que se crea,
 * cierra o asigna se mantiene mientras el app esté abierta.
 */
object SolicitudesCreadasStore {

    private val ejemplos = listOf(
        SolicitudCreada(nombre = "Hospital Roosevelt", ubicacion = "Guatemala", tipoSangre = "O-"),
        SolicitudCreada(nombre = "Hospital San Juan de Dios", ubicacion = "Guatemala", tipoSangre = "A+"),
        SolicitudCreada(nombre = "Hospital Regional de Quetzaltenango", ubicacion = "Quetzaltenango", tipoSangre = "B+"),
        SolicitudCreada(nombre = "IGSS Zona 9", ubicacion = "Guatemala", tipoSangre = "AB-"),
        SolicitudCreada(nombre = "Hospital Nacional de Escuintla", ubicacion = "Escuintla", tipoSangre = "O+"),
        SolicitudCreada(nombre = "Hospital de Cobán", ubicacion = "Alta Verapaz", tipoSangre = "A-"),
    )

    private val _solicitudes = MutableStateFlow(ejemplos)
    val solicitudes: StateFlow<List<SolicitudCreada>> = _solicitudes.asStateFlow()

    /** Las nuevas aparecen arriba de la lista. */
    fun agregar(solicitud: SolicitudCreada) {
        _solicitudes.update { listOf(solicitud) + it }
    }

    /** Cerrar o asignar saca la solicitud de la lista de activas. */
    fun quitar(solicitud: SolicitudCreada) {
        _solicitudes.update { it - solicitud }
    }
}
