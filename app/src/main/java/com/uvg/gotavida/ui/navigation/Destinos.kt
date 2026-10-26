package com.uvg.gotavida.ui.navigation

import com.uvg.gotavida.data.model.RolCuenta
import kotlinx.serialization.Serializable

/**
 * Todos los destinos del app (navegación type-safe). Entre pantallas solo
 * viaja lo mínimo que la siguiente necesita: un id o el rol.
 */
sealed interface Destino {

    // ---------- Auth ----------
    @Serializable data object Splash : Destino
    @Serializable data object Login : Destino
    @Serializable data class RegistroBasico(val rol: RolCuenta) : Destino
    @Serializable data object RegistroDonante : Destino
    @Serializable data object RegistroMedica : Destino

    // ---------- Donante ----------
    @Serializable data object HomeDonante : Destino
    @Serializable data object MapaDonante : Destino
    @Serializable data object PerfilDonante : Destino
    @Serializable data class DetalleSolicitud(val solicitudId: String) : Destino
    @Serializable data class Cuestionario(val solicitudId: String) : Destino
    @Serializable data class Confirmacion(val solicitudId: String) : Destino

    // ---------- Médica / admin ----------
    @Serializable data object DashboardMedico : Destino
    @Serializable data object NuevaSolicitud : Destino
    @Serializable data object SolicitudesCreadas : Destino
    @Serializable data object SolicitudAsignada : Destino
    @Serializable data object SolicitudCerrada : Destino
    @Serializable data object PerfilMedico : Destino

    // ---------- Compartidas ----------
    @Serializable data class Notificaciones(val rol: RolCuenta) : Destino
    @Serializable data class Ajustes(val rol: RolCuenta) : Destino
}
