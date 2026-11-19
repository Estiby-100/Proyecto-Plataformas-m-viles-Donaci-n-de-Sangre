package com.uvg.gotavida.di

import android.content.Context
import com.uvg.gotavida.data.auth.AuthRepository
import com.uvg.gotavida.data.auth.CuentaRepository
import com.uvg.gotavida.data.local.AppDatabase
import com.uvg.gotavida.data.local.NotificacionRepository
import com.uvg.gotavida.data.local.UsuarioRepository
import com.uvg.gotavida.data.remote.InstitucionesRepository
import com.uvg.gotavida.data.remote.SolicitudesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Punto único donde se crean las dependencias del app. Se inicializa una vez
 * en GotaVidaApp. Así los ViewModels pueden pedir lo que necesiten sin que
 * haya que pasarles un Context ni crear una fábrica por cada uno:
 *
 *     val cuentas = ServiceLocator.cuentaRepository
 */
object ServiceLocator {

    private lateinit var database: AppDatabase

    lateinit var usuarioRepository: UsuarioRepository
        private set

    lateinit var notificacionRepository: NotificacionRepository
        private set

    val authRepository: AuthRepository by lazy { AuthRepository() }

    val cuentaRepository: CuentaRepository by lazy {
        CuentaRepository(authRepository, usuarioRepository)
    }

    val solicitudesRepository: SolicitudesRepository by lazy { SolicitudesRepository() }

    val institucionesRepository: InstitucionesRepository by lazy { InstitucionesRepository() }

    fun init(context: Context) {
        database = AppDatabase.crear(context)
        usuarioRepository = UsuarioRepository(database.usuarioDao())
        notificacionRepository = NotificacionRepository(database.notificacionDao())
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { notificacionRepository.inicializarSiEsNecesario() }
        }
    }
}
