package com.uvg.gotavida.ui.vistaadmin.solicitud.nueva_solicitud

data class NuevaSolicitudState(
    val nombreCompleto: String = "",
    val tipoSangre: String = "",
    val signo: String = "",
    val departamento: String = "",
    val detalles: String = ""
)

sealed interface NuevaSolicitudEvent {

    data class NombreChanged(
        val nombre: String
    ) : NuevaSolicitudEvent

    data class TipoSangreChanged(
        val tipo: String
    ) : NuevaSolicitudEvent

    data class SignoChanged(
        val signo: String
    ) : NuevaSolicitudEvent

    data class DepartamentoChanged(
        val departamento: String
    ) : NuevaSolicitudEvent

    data class DetallesChanged(
        val detalles: String
    ) : NuevaSolicitudEvent

    data object EnviarSolicitud : NuevaSolicitudEvent

    data object BackClick : NuevaSolicitudEvent
}