package com.uvg.gotavida.data.auth

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

/**
 * Único punto de contacto con Firebase Authentication (la llamada a internet
 * del proyecto). Solo maneja correo y contraseña: el rol y el perfil viven en
 * la base local (ver CuentaRepository).
 */
class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
) {

    /** uid del usuario con sesión iniciada, o null si no hay sesión. */
    val uidActual: String?
        get() = auth.currentUser?.uid

    suspend fun registrar(correo: String, contrasena: String): AuthResult<String> = ejecutar {
        val resultado = auth.createUserWithEmailAndPassword(correo.trim(), contrasena).await()
        resultado.user?.uid ?: error("Firebase no devolvió el usuario")
    }

    suspend fun iniciarSesion(correo: String, contrasena: String): AuthResult<String> = ejecutar {
        val resultado = auth.signInWithEmailAndPassword(correo.trim(), contrasena).await()
        resultado.user?.uid ?: error("Firebase no devolvió el usuario")
    }

    fun cerrarSesion() {
        auth.signOut()
    }

    private suspend fun <T> ejecutar(bloque: suspend () -> T): AuthResult<T> =
        try {
            AuthResult.Exito(bloque())
        } catch (e: CancellationException) {
            throw e // nunca tragarse una cancelación de corrutina
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error de Firebase Authentication", e)
            AuthResult.Fallo(traducir(e))
        }

    // El orden importa: WeakPassword es un tipo de InvalidCredentials.
    private fun traducir(e: Exception): AuthError = when (e) {
        is FirebaseAuthWeakPasswordException -> AuthError.CONTRASENA_DEBIL
        is FirebaseAuthUserCollisionException -> AuthError.CORREO_YA_REGISTRADO
        is FirebaseAuthInvalidCredentialsException ->
            if (e.errorCode == "ERROR_INVALID_EMAIL") AuthError.CORREO_INVALIDO
            else AuthError.CREDENCIALES_INCORRECTAS
        is FirebaseAuthInvalidUserException -> AuthError.CREDENCIALES_INCORRECTAS
        is FirebaseNetworkException -> AuthError.SIN_CONEXION
        else -> AuthError.DESCONOCIDO
    }
}
