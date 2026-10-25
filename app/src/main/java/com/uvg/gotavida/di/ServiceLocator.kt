package com.uvg.gotavida.di

import android.content.Context
import com.uvg.gotavida.data.auth.AuthRepository
import com.uvg.gotavida.data.auth.CuentaRepository
import com.uvg.gotavida.data.local.AppDatabase
import com.uvg.gotavida.data.local.UsuarioRepository

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

    val authRepository: AuthRepository by lazy { AuthRepository() }

    val cuentaRepository: CuentaRepository by lazy {
        CuentaRepository(authRepository, usuarioRepository)
    }

    fun init(context: Context) {
        database = AppDatabase.crear(context)
        usuarioRepository = UsuarioRepository(database.usuarioDao())
    }
}
