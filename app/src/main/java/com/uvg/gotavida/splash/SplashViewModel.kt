package com.uvg.gotavida.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    var state by mutableStateOf(SplashState())
        private set

    init {
        Splash()
    }

    private fun Splash() {
        viewModelScope.launch {

            delay(2000)

            state = state.copy(
                isLoading = false
            )
        }
    }
}