package com.uvg.gotavida

import android.app.Application
import com.uvg.gotavida.di.ServiceLocator

// Clase principal de la aplicación. Se ejecuta al arrancar e inicializa las dependencias globales.
class GotaVidaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Prepara los repositorios y la base de datos local desde el inicio
        ServiceLocator.init(this)
    }
}
