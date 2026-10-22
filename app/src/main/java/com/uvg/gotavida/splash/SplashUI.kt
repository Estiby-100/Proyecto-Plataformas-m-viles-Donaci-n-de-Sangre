package com.uvg.gotavida.splash

data class SplashState(
    val isLoading: Boolean = true
)

sealed interface SplashEvent {
    data object Start : SplashEvent
}