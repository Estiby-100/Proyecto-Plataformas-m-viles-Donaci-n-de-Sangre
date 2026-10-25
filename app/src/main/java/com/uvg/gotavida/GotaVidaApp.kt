package com.uvg.gotavida

import android.app.Application
import com.uvg.gotavida.di.ServiceLocator

class GotaVidaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}
