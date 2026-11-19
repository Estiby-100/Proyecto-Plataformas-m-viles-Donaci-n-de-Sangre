package com.uvg.gotavida.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uvg.gotavida.data.model.EstadoCuenta
import com.uvg.gotavida.data.model.RolCuenta
import com.uvg.gotavida.data.model.TipoSangre

// Representa el perfil del usuario guardado localmente en el teléfono.
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
