package com.uvg.gotavida.data.auth

import com.uvg.gotavida.data.local.UsuarioEntity
import com.uvg.gotavida.data.local.UsuarioRepository
import com.uvg.gotavida.data.model.EstadoCuenta
import com.uvg.gotavida.data.model.RolCuenta
import com.uvg.gotavida.data.model.TipoSangre

/**
 * Junta Firebase (quién eres) con Room (cómo es tu perfil). Es lo que usan
 * los ViewModels de registro e inicio de sesión.
 *
 * Flujo de registro en dos pasos:
 *  1. registrarCuenta(...)           -> crea la cuenta en Firebase y un perfil base en Room.
 *  2. completarPerfilDonante(...) o
 *     completarPerfilMedica(...)     -> agrega los datos propios de cada rol.
 */
class CuentaRepository(
    private val auth: AuthRepository,
    private val usuarios: UsuarioRepository,
) {

    suspend fun registrarCuenta(
        nombre: String,
        correo: String,
        contrasena: String,
        rol: RolCuenta,
    ): AuthResult<UsuarioEntity> =
        when (val resultado = auth.registrar(correo, contrasena)) {
            is AuthResult.Fallo -> resultado
            is AuthResult.Exito -> {
                val usuario = UsuarioEntity(
                    uid = resultado.valor,
                    nombre = nombre.trim(),
                    correo = correo.trim(),
                    rol = rol,
                    // La cuenta médica queda en revisión hasta que alguien la verifique.
                    estadoCuenta = if (rol == RolCuenta.MEDICA) EstadoCuenta.EN_REVISION else EstadoCuenta.ACTIVA,
                )
                usuarios.guardar(usuario)
                AuthResult.Exito(usuario)
            }
        }

    suspend fun completarPerfilDonante(
        tipoSangre: TipoSangre?,
        ciudad: String,
    ): AuthResult<UsuarioEntity> =
        actualizar { it.copy(tipoSangre = tipoSangre, ciudad = ciudad.trim()) }

    suspend fun completarPerfilMedica(
        institucion: String,
        numeroColegiado: String?,
    ): AuthResult<UsuarioEntity> =
        actualizar {
            it.copy(
                institucion = institucion.trim(),
                numeroColegiado = numeroColegiado?.trim()?.takeIf { texto -> texto.isNotEmpty() },
            )
        }

    suspend fun iniciarSesion(correo: String, contrasena: String): AuthResult<UsuarioEntity> =
        when (val resultado = auth.iniciarSesion(correo, contrasena)) {
            is AuthResult.Fallo -> resultado
            is AuthResult.Exito -> {
                val usuario = usuarios.obtener(resultado.valor)
                if (usuario == null) {
                    // Hay cuenta en Firebase pero este teléfono no tiene su perfil.
                    auth.cerrarSesion()
                    AuthResult.Fallo(AuthError.PERFIL_NO_ENCONTRADO)
                } else {
                    AuthResult.Exito(usuario)
                }
            }
        }

    /** Perfil del usuario con sesión iniciada, o null si no hay sesión. */
    suspend fun sesionActual(): UsuarioEntity? =
        auth.uidActual?.let { usuarios.obtener(it) }

    /** Cierra la sesión de Firebase. El perfil local se conserva para el próximo inicio de sesión. */
    fun cerrarSesion() {
        auth.cerrarSesion()
    }

    private suspend fun actualizar(cambio: (UsuarioEntity) -> UsuarioEntity): AuthResult<UsuarioEntity> {
        val uid = auth.uidActual ?: return AuthResult.Fallo(AuthError.SIN_SESION)
        val actual = usuarios.obtener(uid) ?: return AuthResult.Fallo(AuthError.PERFIL_NO_ENCONTRADO)
        val nuevo = cambio(actual)
        usuarios.guardar(nuevo)
        return AuthResult.Exito(nuevo)
    }
}
