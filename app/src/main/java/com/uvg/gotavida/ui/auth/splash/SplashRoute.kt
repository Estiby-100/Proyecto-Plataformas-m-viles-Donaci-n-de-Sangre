package com.uvg.gotavida.ui.auth.splash

import androidx.compose.runtime.Composable

/**
 * En esta fase no hay navegación ni lógica real — el Route es un
 * paso directo hacia el Screen. Cuando se agregue navegación, aquí es
 * donde se conectará con el NavController (o similar), no dentro del
 * Screen.
 */
@Composable
fun SplashRoute() {
    SplashScreen(
        onDonorClick = { /* TODO: navegar a Registro básico (contexto donante) */ },
        onMedicalClick = { /* TODO: navegar a Registro básico (contexto médica) */ },
        onLoginClick = { /* TODO: navegar a Login */ },
    )
}
