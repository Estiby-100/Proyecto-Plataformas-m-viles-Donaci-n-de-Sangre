package com.uvg.gotavida.data.model

import kotlinx.serialization.Serializable

// @Serializable para poder usarlo como argumento en los destinos type-safe.
@Serializable
enum class RolCuenta {
    DONANTE,
    MEDICA,
}
