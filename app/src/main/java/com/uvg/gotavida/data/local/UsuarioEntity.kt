package com.uvg.gotavida.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uvg.gotavida.data.model.EstadoCuenta
import com.uvg.gotavida.data.model.RolCuenta
import com.uvg.gotavida.data.model.TipoSangre

/**
 * Perfil del usuario guardado en el teléfono. Firebase Authentication solo
 * guarda correo y contraseña; el rol (donante o médica) y el resto del perfil
 * viven aquí. El uid es el mismo que entrega Firebase.
 *
 * Los campos de donante (tipoSangre, ciudad) y de médica (institucion,
 * numeroColegiado) son opcionales porque cada rol llena solo los suyos.
 */
@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey val uid: String,
    val nombre: String,
    val correo: String,
    val rol: RolCuenta,
    val estadoCuenta: EstadoCuenta,
    val tipoSangre: TipoSangre? = null,
    val ciudad: String? = null,
    val institucion: String? = null,
    val numeroColegiado: String? = null,
)
