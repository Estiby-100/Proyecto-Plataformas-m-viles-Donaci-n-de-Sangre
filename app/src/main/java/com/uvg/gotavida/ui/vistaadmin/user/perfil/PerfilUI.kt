package com.uvg.gotavida.ui.vistaadmin.user.perfil

data class Perfil(
    val nombre: String,
    val hospital: String,
    val correo: String,
    val telefono: String,
    val institucion: String,
    val departamento: String,
    val rol: String,
    val miembro: String
)

data class PerfilState(

    val perfil: Perfil = Perfil(
        nombre = "Dr. Juan Pérez",
        hospital = "Hospital Roosevelt",
        correo = "juan.perez@hospital.gt",
        telefono = "+502 5555-1234",
        institucion = "Hospital Roosevelt",
        departamento = "Guatemala",
        rol = "Administrador",
        miembro = "Enero 2024"
    )
)

sealed interface PerfilEvent {

    data object BackClick : PerfilEvent
    data object CerrarSesion : PerfilEvent
    data object Ajustes : PerfilEvent
}