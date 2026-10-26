package com.uvg.gotavida.ui.auth.splash

import com.uvg.gotavida.data.model.RolCuenta

/**
 * Estado de la pantalla de bienvenida (elegir rol o iniciar sesión).
 * Al abrir el app se revisa si ya hay una sesión iniciada: si la hay,
 * sesionActiva trae el rol y la navegación salta directo a su home.
 */
data class SplashUiState(
    val verificandoSesion: Boolean = true,
    val sesionActiva: RolCuenta? = null,
)

sealed interface SplashEvent {
    data object DonanteClick : SplashEvent
    data object MedicaClick : SplashEvent
    data object LoginClick : SplashEvent

    /** La vista ya navegó con sesionActiva; se limpia para no repetirlo. */
    data object SesionAtendida : SplashEvent
}
