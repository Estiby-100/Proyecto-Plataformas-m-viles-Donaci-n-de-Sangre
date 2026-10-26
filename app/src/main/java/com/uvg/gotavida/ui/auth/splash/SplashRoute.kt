package com.uvg.gotavida.ui.auth.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.gotavida.data.model.RolCuenta
import com.uvg.gotavida.splash.SplashScreen as CargandoScreen

@Composable
fun SplashRoute(
    onRolElegido: (RolCuenta) -> Unit,
    onLoginClick: () -> Unit,
    onSesionActiva: (RolCuenta) -> Unit,
    viewModel: SplashViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.sesionActiva) {
        val rol = state.sesionActiva ?: return@LaunchedEffect
        viewModel.onEvent(SplashEvent.SesionAtendida)
        onSesionActiva(rol)
    }

    if (state.verificandoSesion || state.sesionActiva != null) {
        CargandoScreen(message = "Cargando...")
    } else {
        SplashScreen(
            onDonorClick = {
                viewModel.onEvent(SplashEvent.DonanteClick)
                onRolElegido(RolCuenta.DONANTE)
            },
            onMedicalClick = {
                viewModel.onEvent(SplashEvent.MedicaClick)
                onRolElegido(RolCuenta.MEDICA)
            },
            onLoginClick = {
                viewModel.onEvent(SplashEvent.LoginClick)
                onLoginClick()
            },
        )
    }
}
