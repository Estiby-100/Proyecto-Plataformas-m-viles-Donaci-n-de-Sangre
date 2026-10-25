package com.uvg.gotavida.data.auth

/**
 * Qué pudo salir mal. Los ViewModels convierten esto en el texto que ve el
 * usuario; el repositorio nunca devuelve mensajes de Firebase tal cual.
 */
enum class AuthError {
    CORREO_INVALIDO,
    CONTRASENA_DEBIL,
    CORREO_YA_REGISTRADO,
    CREDENCIALES_INCORRECTAS,
    SIN_CONEXION,
    PERFIL_NO_ENCONTRADO,
    SIN_SESION,
    DESCONOCIDO,
}

sealed interface AuthResult<out T> {
    data class Exito<out T>(val valor: T) : AuthResult<T>
    data class Fallo(val error: AuthError) : AuthResult<Nothing>
}
