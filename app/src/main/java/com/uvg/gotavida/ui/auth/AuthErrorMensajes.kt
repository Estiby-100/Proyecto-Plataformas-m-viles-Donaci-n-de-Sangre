package com.uvg.gotavida.ui.auth

import com.uvg.gotavida.data.auth.AuthError

/** Texto que ve el usuario para cada error de autenticación. */
fun AuthError.aMensaje(): String = when (this) {
    AuthError.CORREO_INVALIDO -> "Ingresa un correo válido."
    AuthError.CONTRASENA_DEBIL -> "La contraseña debe tener al menos 6 caracteres."
    AuthError.CORREO_YA_REGISTRADO -> "Ya existe una cuenta con este correo."
    AuthError.CREDENCIALES_INCORRECTAS -> "Correo o contraseña incorrectos."
    AuthError.SIN_CONEXION -> "Sin conexión. Revisa tu internet e intenta de nuevo."
    AuthError.PERFIL_NO_ENCONTRADO -> "Esta cuenta no tiene perfil en este teléfono. Regístrate de nuevo."
    AuthError.SIN_SESION -> "Tu sesión expiró. Inicia sesión de nuevo."
    AuthError.DESCONOCIDO -> "Algo salió mal. Intenta de nuevo."
}
