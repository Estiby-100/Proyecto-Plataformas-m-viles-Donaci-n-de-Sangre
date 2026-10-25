package com.uvg.gotavida.data.model

enum class TipoInstitucion {
    HOSPITAL,
    BANCO_DE_SANGRE,
}

/**
 * Hospital o banco de sangre. Se usa, por ejemplo, para autocompletar el
 * campo "Nombre de la institución" en el registro de cuentas médicas.
 */
data class Institucion(
    val id: String,
    val nombre: String,
    val ubicacion: String,
    val tipo: TipoInstitucion,
)
