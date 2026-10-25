package com.uvg.gotavida.ui.donante.mapa

import com.uvg.gotavida.data.model.LugarMapa

data class MapaState(
    val lugares: List<LugarMapa> = emptyList(),
    val vista: Boolean = false,
    val permiso: Boolean = true,
    val seleccionado: LugarMapa? = null
)

sealed interface MapaEvent {
    data object Vista : MapaEvent
    data class Lugar(
        val lugar: LugarMapa
    ) : MapaEvent
    data object Cerrar : MapaEvent
    data object Ubicacion : MapaEvent
}