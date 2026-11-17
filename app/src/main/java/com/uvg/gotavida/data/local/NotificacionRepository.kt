package com.uvg.gotavida.data.local

import com.uvg.gotavida.data.fake.FakeNotificacionesDataSource
import com.uvg.gotavida.data.model.Notificacion
import com.uvg.gotavida.data.model.NotificacionTipo
import com.uvg.gotavida.data.model.RolCuenta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificacionRepository(
    private val dao: NotificacionDao
) {

    suspend fun inicializarSiEsNecesario() {
        if (dao.contar() == 0) {
            val semillas = mutableListOf<NotificacionEntity>()

            FakeNotificacionesDataSource.donante.forEachIndexed { idx, n ->
                semillas.add(
                    NotificacionEntity(
                        id = n.id,
                        rol = RolCuenta.DONANTE.name,
                        tipo = n.tipo.name,
                        titulo = n.titulo,
                        subtitulo = n.subtitulo,
                        tiempoRelativo = n.tiempoRelativo,
                        leida = n.leida,
                        timestamp = System.currentTimeMillis() - (idx * 3600_000L)
                    )
                )
            }

            FakeNotificacionesDataSource.medica.forEachIndexed { idx, n ->
                semillas.add(
                    NotificacionEntity(
                        id = n.id,
                        rol = RolCuenta.MEDICA.name,
                        tipo = n.tipo.name,
                        titulo = n.titulo,
                        subtitulo = n.subtitulo,
                        tiempoRelativo = n.tiempoRelativo,
                        leida = n.leida,
                        timestamp = System.currentTimeMillis() - (idx * 3600_000L)
                    )
                )
            }

            dao.insertarVarias(semillas)
        }
    }

    fun observarDe(rol: RolCuenta): Flow<List<Notificacion>> {
        return dao.observarPorRol(rol.name).map { lista ->
            lista.map { entity ->
                Notificacion(
                    id = entity.id,
                    tipo = runCatching { NotificacionTipo.valueOf(entity.tipo) }.getOrDefault(NotificacionTipo.SOLICITUD),
                    titulo = entity.titulo,
                    subtitulo = entity.subtitulo,
                    tiempoRelativo = entity.tiempoRelativo,
                    leida = entity.leida
                )
            }
        }
    }

    suspend fun agregar(
        rol: RolCuenta,
        tipo: NotificacionTipo,
        titulo: String,
        subtitulo: String,
        tiempoRelativo: String = "Hace un momento"
    ) {
        val entity = NotificacionEntity(
            rol = rol.name,
            tipo = tipo.name,
            titulo = titulo,
            subtitulo = subtitulo,
            tiempoRelativo = tiempoRelativo,
            leida = false,
            timestamp = System.currentTimeMillis()
        )
        dao.insertar(entity)
    }

    suspend fun marcarLeida(id: String) {
        dao.marcarLeida(id)
    }

    suspend fun marcarTodas(rol: RolCuenta) {
        dao.marcarTodasLeidas(rol.name)
    }
}
