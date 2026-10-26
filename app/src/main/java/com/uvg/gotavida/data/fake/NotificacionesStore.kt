package com.uvg.gotavida.data.fake

import com.uvg.gotavida.data.model.Notificacion
import com.uvg.gotavida.data.model.RolCuenta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Notificaciones de cada rol, en memoria. Así las que se marcan como
 * leídas siguen leídas al salir y volver a entrar a la pantalla.
 */
object NotificacionesStore {

    private val porRol = mapOf(
        RolCuenta.DONANTE to MutableStateFlow(FakeNotificacionesDataSource.donante),
        RolCuenta.MEDICA to MutableStateFlow(FakeNotificacionesDataSource.medica),
    )

    fun de(rol: RolCuenta): StateFlow<List<Notificacion>> = porRol.getValue(rol).asStateFlow()

    fun marcarLeida(rol: RolCuenta, id: String) {
        porRol.getValue(rol).update { lista ->
            lista.map { if (it.id == id) it.copy(leida = true) else it }
        }
    }

    fun marcarTodas(rol: RolCuenta) {
        porRol.getValue(rol).update { lista -> lista.map { it.copy(leida = true) } }
    }
}
