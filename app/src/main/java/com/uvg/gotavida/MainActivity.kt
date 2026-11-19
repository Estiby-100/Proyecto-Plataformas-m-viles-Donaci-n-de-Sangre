package com.uvg.gotavida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.uvg.gotavida.ui.navigation.AppNavHost
import com.uvg.gotavida.ui.theme.GotaVidaTheme

// Actividad principal que carga el tema de la app y arranca la navegación general.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GotaVidaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Carga el grafo de navegación completo
                    AppNavHost()
                }
            }
        }
    }
}
