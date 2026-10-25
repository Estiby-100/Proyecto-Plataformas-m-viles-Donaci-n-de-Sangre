package com.uvg.gotavida.ui.donante.perfil

import com.uvg.gotavida.data.model.Insignia
import com.uvg.gotavida.data.model.ItemHistorial

data class PerfilState(
    val nombre: String = "Esteban Sánchez",
    val sangre: String = "O+",
    val disponible: Boolean = true,
    val historial: List<ItemHistorial> = emptyList(),
    val insignias: List<Insignia> = emptyList(),
    val cargando: Boolean = false
)

sealed interface PerfilEvent {
    data class Disponibilidad(
        val disponible: Boolean
    ) : PerfilEvent
}
