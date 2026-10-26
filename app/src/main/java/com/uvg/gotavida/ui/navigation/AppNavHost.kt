package com.uvg.gotavida.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.uvg.gotavida.data.model.RolCuenta
import com.uvg.gotavida.ui.auth.login.LoginRoute
import com.uvg.gotavida.ui.auth.registrobasico.RegistroBasicoRoute
import com.uvg.gotavida.ui.auth.registrodonante.RegistroDonanteRoute
import com.uvg.gotavida.ui.auth.registromedica.RegistroMedicaRoute
import com.uvg.gotavida.ui.auth.splash.SplashRoute
import com.uvg.gotavida.ui.donante.common.DonanteBottomNavigation
import com.uvg.gotavida.ui.donante.common.DonanteTab
import com.uvg.gotavida.ui.donante.confirmacion.ConfirmacionRoute
import com.uvg.gotavida.ui.donante.cuestionario.CuestionarioRoute
import com.uvg.gotavida.ui.donante.detalle.DetalleSolicitudRoute
import com.uvg.gotavida.ui.donante.home.HomeDonanteRoute
import com.uvg.gotavida.ui.donante.mapa.MapaRoute
import com.uvg.gotavida.ui.donante.perfil.PerfilRoute
import com.uvg.gotavida.ui.shared.ajustes.AjustesRoute
import com.uvg.gotavida.ui.shared.notificaciones.NotificacionesRoute
import com.uvg.gotavida.ui.vistaadmin.NuevaSolicitud
import com.uvg.gotavida.ui.vistaadmin.user.home.BloodDonationScreen
import com.uvg.gotavida.vistaadmin.SolicitudAsignadaScreen
import com.uvg.gotavida.vistaadmin.SolicitudCerradaScreen
import com.uvg.gotavida.vistaadmin.solicitud.solicitudes.SolicitudesScreen
import com.uvg.gotavida.vistaadmin.user.perfil.PerfilScreen as PerfilMedicoScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Destino.Splash,
    ) {

        // ================= Auth =================

        composable<Destino.Splash> {
            SplashRoute(
                onRolElegido = { rol -> navController.navigate(Destino.RegistroBasico(rol)) },
                onLoginClick = { navController.navigate(Destino.Login) },
                onSesionActiva = { rol -> navController.limpiarYNavegar(homeDe(rol)) },
            )
        }

        composable<Destino.Login> {
            LoginRoute(
                onLoginExitoso = { rol -> navController.limpiarYNavegar(homeDe(rol)) },
                onBackClick = { navController.volverO(Destino.Splash) },
                // "Regístrate" lleva a Splash, que es donde se elige el rol.
                onRegisterClick = { navController.volverO(Destino.Splash) },
            )
        }

        composable<Destino.RegistroBasico> { entry ->
            val args = entry.toRoute<Destino.RegistroBasico>()
            RegistroBasicoRoute(
                rol = args.rol,
                onCuentaCreada = {
                    val siguiente = when (args.rol) {
                        RolCuenta.DONANTE -> Destino.RegistroDonante
                        RolCuenta.MEDICA -> Destino.RegistroMedica
                    }
                    // La cuenta ya existe: no se puede volver a este formulario.
                    navController.navigate(siguiente) {
                        popUpTo<Destino.RegistroBasico> { inclusive = true }
                    }
                },
                onBackClick = { navController.volverO(Destino.Splash) },
            )
        }

        composable<Destino.RegistroDonante> {
            RegistroDonanteRoute(
                onPerfilCompleto = { navController.limpiarYNavegar(Destino.HomeDonante) },
                onBackClick = { navController.volverO(Destino.Splash) },
            )
        }

        composable<Destino.RegistroMedica> {
            RegistroMedicaRoute(
                // Cuenta en revisión: vuelve a Splash; al dashboard solo se entra con login.
                onVolverAlInicio = { navController.limpiarYNavegar(Destino.Splash) },
                onBackClick = { navController.volverO(Destino.Splash) },
            )
        }

        // ================= Donante =================

        composable<Destino.HomeDonante> {
            HomeDonanteRoute(
                onVerDetalle = { id -> navController.navigate(Destino.DetalleSolicitud(id)) },
                onNotificacionesClick = {
                    navController.navigate(Destino.Notificaciones(RolCuenta.DONANTE))
                },
                onTabSeleccionado = { tab -> navController.irATab(tab) },
            )
        }

        composable<Destino.MapaDonante> {
            ConMenuDonante(tab = DonanteTab.MAPA, onTab = { navController.irATab(it) }) {
                MapaRoute()
            }
        }

        composable<Destino.PerfilDonante> {
            ConMenuDonante(tab = DonanteTab.PERFIL, onTab = { navController.irATab(it) }) {
                PerfilRoute(
                    onAjustesClick = {
                        navController.navigate(Destino.Ajustes(RolCuenta.DONANTE))
                    },
                )
            }
        }

        composable<Destino.DetalleSolicitud> { entry ->
            val args = entry.toRoute<Destino.DetalleSolicitud>()
            DetalleSolicitudRoute(
                solicitudId = args.solicitudId,
                onPuedoDonarClick = {
                    navController.navigate(Destino.Cuestionario(args.solicitudId))
                },
                onVerEnMapaClick = { navController.irATab(DonanteTab.MAPA) },
                onAtrasClick = { navController.volverO(Destino.HomeDonante) },
            )
        }

        composable<Destino.Cuestionario> { entry ->
            val args = entry.toRoute<Destino.Cuestionario>()
            CuestionarioRoute(
                onCerrarClick = { navController.volverO(Destino.HomeDonante) },
                onContinuarClick = {
                    navController.navigate(Destino.Confirmacion(args.solicitudId)) {
                        popUpTo<Destino.Cuestionario> { inclusive = true }
                    }
                },
                onVolverInicioClick = { navController.volverAlHomeDonante() },
            )
        }

        composable<Destino.Confirmacion> { entry ->
            val args = entry.toRoute<Destino.Confirmacion>()
            ConfirmacionRoute(
                solicitudId = args.solicitudId,
                onAbrirEnMapaClick = { navController.irATab(DonanteTab.MAPA) },
                onEntendidoClick = { navController.volverAlHomeDonante() },
                onParticipacionCancelada = { navController.volverAlHomeDonante() },
            )
        }

        // ================= Médica / admin =================

        composable<Destino.DashboardMedico> {
            BloodDonationScreen(
                onCrearSolicitud = { navController.navigate(Destino.NuevaSolicitud) },
                onVerSolicitudes = { navController.navigate(Destino.SolicitudesCreadas) },
                onPerfilClick = { navController.navigate(Destino.PerfilMedico) },
                onNotificacionesClick = {
                    navController.navigate(Destino.Notificaciones(RolCuenta.MEDICA))
                },
            )
        }

        composable<Destino.NuevaSolicitud> {
            NuevaSolicitud(
                onBackClick = { navController.volverO(Destino.DashboardMedico) },
                onEnviarClick = {
                    navController.navigate(Destino.SolicitudesCreadas) {
                        popUpTo<Destino.NuevaSolicitud> { inclusive = true }
                    }
                },
            )
        }

        composable<Destino.SolicitudesCreadas> {
            SolicitudesScreen(
                onBackClick = { navController.volverO(Destino.DashboardMedico) },
                onCerrarSolicitud = { navController.navigate(Destino.SolicitudCerrada) },
                onAsignarSolicitud = { navController.navigate(Destino.SolicitudAsignada) },
            )
        }

        composable<Destino.SolicitudAsignada> {
            SolicitudAsignadaScreen(
                onVolverAlDashboard = { navController.volverAlDashboard() },
            )
        }

        composable<Destino.SolicitudCerrada> {
            SolicitudCerradaScreen(
                onVolverAlDashboard = { navController.volverAlDashboard() },
            )
        }

        composable<Destino.PerfilMedico> {
            PerfilMedicoScreen(
                onBackClick = { navController.volverO(Destino.DashboardMedico) },
                onCerrarSesion = { navController.limpiarYNavegar(Destino.Splash) },
                onAjustesClick = {
                    navController.navigate(Destino.Ajustes(RolCuenta.MEDICA))
                },
            )
        }

        // ================= Compartidas =================

        composable<Destino.Notificaciones> { entry ->
            val args = entry.toRoute<Destino.Notificaciones>()
            NotificacionesRoute(
                rol = args.rol,
                onBackClick = { navController.volverO(homeDe(args.rol)) },
            )
        }

        composable<Destino.Ajustes> { entry ->
            val args = entry.toRoute<Destino.Ajustes>()
            AjustesRoute(
                rol = args.rol,
                onBackClick = { navController.volverO(homeDe(args.rol)) },
                onSesionCerrada = { navController.limpiarYNavegar(Destino.Splash) },
            )
        }
    }
}

/** Home que le corresponde a cada rol. */
private fun homeDe(rol: RolCuenta): Destino = when (rol) {
    RolCuenta.DONANTE -> Destino.HomeDonante
    RolCuenta.MEDICA -> Destino.DashboardMedico
}

/** Vuelve a la pantalla anterior; si no hay, va al destino de respaldo. */
private fun NavHostController.volverO(respaldo: Destino) {
    if (previousBackStackEntry != null) {
        popBackStack()
    } else {
        limpiarYNavegar(respaldo)
    }
}

/** Navega dejando ese destino como único en la pila (login, logout, fin de registro). */
private fun NavHostController.limpiarYNavegar(destino: Destino) {
    navigate(destino) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}

/** Botones "Volver al dashboard": siempre al dashboard médico. */
private fun NavHostController.volverAlDashboard() {
    if (!popBackStack<Destino.DashboardMedico>(inclusive = false)) {
        limpiarYNavegar(Destino.DashboardMedico)
    }
}

/** Regresa al Home del donante cerrando el flujo de donación. */
private fun NavHostController.volverAlHomeDonante() {
    if (!popBackStack<Destino.HomeDonante>(inclusive = false)) {
        limpiarYNavegar(Destino.HomeDonante)
    }
}

/** Cambia de pestaña del menú inferior del donante sin apilar pestañas repetidas. */
private fun NavHostController.irATab(tab: DonanteTab) {
    val destino = when (tab) {
        DonanteTab.HOME -> Destino.HomeDonante
        DonanteTab.MAPA -> Destino.MapaDonante
        DonanteTab.PERFIL -> Destino.PerfilDonante
    }
    navigate(destino) {
        popUpTo<Destino.HomeDonante> { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * Mapa y Perfil no traen menú inferior propio (solo el Home lo dibuja),
 * así que aquí se les agrega sin tocar esas pantallas.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ConMenuDonante(
    tab: DonanteTab,
    onTab: (DonanteTab) -> Unit,
    contenido: @Composable () -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            DonanteBottomNavigation(tabSeleccionado = tab, onTabSeleccionado = onTab)
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
        ) {
            contenido()
        }
    }
}
