package com.uvg.gotavida.ui.vistaadmin.user.home

data class HomeState(

    val userName: String = "Administrador",

    val location: String = "Guatemala",

    val requestCount: Int = 4,

    val bloodTypes: List<BloodType> = listOf(

        BloodType("O+", 20, BloodColorKey.PRIMARY),
        BloodType("A+", 25, BloodColorKey.PRIMARY),
        BloodType("B+", 15, BloodColorKey.PRIMARY),
        BloodType("AB+", 5, BloodColorKey.PRIMARY),
        BloodType("O-", 8, BloodColorKey.PRIMARY),
        BloodType("A-", 3, BloodColorKey.PRIMARY),
        BloodType("B-", 2, BloodColorKey.PRIMARY),
        BloodType("AB-", 2, BloodColorKey.PRIMARY)
    )
)

sealed interface HomeEvent {

    data object CrearSolicitud : HomeEvent

    data object VerSolicitudes : HomeEvent

    data object Perfil : HomeEvent

    data object Notificaciones : HomeEvent
}