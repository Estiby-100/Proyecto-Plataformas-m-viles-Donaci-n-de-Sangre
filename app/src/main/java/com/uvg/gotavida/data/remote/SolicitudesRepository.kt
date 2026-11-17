package com.uvg.gotavida.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.uvg.gotavida.data.fake.FakeHospitalesDataSource
import com.uvg.gotavida.data.model.NivelUrgencia
import com.uvg.gotavida.data.model.SolicitudCreada
import com.uvg.gotavida.data.model.SolicitudDonacion
import com.uvg.gotavida.data.model.TipoPublicacion
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull

/** Estados posibles de una solicitud publicada por una médica. */
enum class EstadoSolicitud {
    ACTIVA,
    CERRADA,
    ASIGNADA,
}

/**
 * Único punto de acceso a las solicitudes guardadas en Cloud Firestore
 * (colección "solicitudes"). La médica las crea y la ve el donante, por eso
 * viven en la nube y no en Room.
 *
 * Los ViewModels usan esta clase y nunca Firestore directamente.
 */
class SolicitudesRepository {

    // lazy: así el @Preview no necesita Firebase hasta que alguien lo use de verdad.
    private val coleccion by lazy { FirebaseFirestore.getInstance().collection(COLECCION) }

    /** Solicitudes activas de todas las instituciones, en tiempo real. Es el feed del donante. */
    fun observarActivas(): Flow<List<SolicitudDonacion>> = callbackFlow {
        val registro = coleccion
            .whereEqualTo(CAMPO_ESTADO, EstadoSolicitud.ACTIVA.name)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val lista = snapshot?.documents.orEmpty()
                    .sortedByDescending { it.fechaCreacion() }
                    .mapNotNull { it.aSolicitudDonacion() }
                trySend(lista)
            }
        awaitClose { registro.remove() }
    }

    /** Solicitudes activas creadas por una médica, en tiempo real. Es la lista del dashboard. */
    fun observarDeMedica(medicaUid: String): Flow<List<SolicitudCreada>> = callbackFlow {
        val registro = coleccion
            .whereEqualTo(CAMPO_MEDICA, medicaUid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                // El filtro por estado y el orden se hacen aquí para no pedir un índice en Firestore.
                val lista = snapshot?.documents.orEmpty()
                    .filter { it.getString(CAMPO_ESTADO) == EstadoSolicitud.ACTIVA.name }
                    .sortedByDescending { it.fechaCreacion() }
                    .mapNotNull { it.aSolicitudCreada() }
                trySend(lista)
            }
        awaitClose { registro.remove() }
    }

    /** Una solicitud por su id, o null si no existe. */
    suspend fun obtenerPorId(id: String): SolicitudDonacion? =
        coleccion.document(id).get().await().aSolicitudDonacion()

    suspend fun crear(
        medicaUid: String,
        institucion: String,
        nombrePaciente: String,
        zona: String,
        tipoSangre: String,
        detalles: String,
        tipoPublicacion: TipoPublicacion = TipoPublicacion.SOLICITUD_SANGRE
    ) {
        // Si la institución está en nuestra lista de hospitales, usamos su dirección.
        val hospital = FakeHospitalesDataSource.buscar(institucion).firstOrNull()
        val datos = mapOf(
            CAMPO_MEDICA to medicaUid,
            "institucion" to institucion,
            "nombrePaciente" to nombrePaciente,
            "zona" to zona,
            "tipoSangre" to tipoSangre,
            "detalles" to detalles,
            "tipoPublicacion" to tipoPublicacion.name,
            "direccion" to (hospital?.ubicacion ?: zona),
            CAMPO_ESTADO to EstadoSolicitud.ACTIVA.name,
            "creadaEn" to FieldValue.serverTimestamp(),
        )
        // Sin internet Firestore guarda la escritura en el teléfono y la envía al volver
        // la conexión, pero el await() no termina hasta que el servidor responde. El
        // límite de tiempo evita que la pantalla se quede cargando para siempre.
        withTimeoutOrNull(TIEMPO_ESPERA_MS) { coleccion.add(datos).await() }
    }

    suspend fun cambiarEstado(id: String, estado: EstadoSolicitud) {
        withTimeoutOrNull(TIEMPO_ESPERA_MS) {
            coleccion.document(id).update(CAMPO_ESTADO, estado.name).await()
        }
    }

    private fun DocumentSnapshot.fechaCreacion(): Long =
        // Mientras el servidor no confirma, la fecha es null: se trata como la más reciente.
        getTimestamp("creadaEn")?.seconds ?: Long.MAX_VALUE

    private fun DocumentSnapshot.aSolicitudDonacion(): SolicitudDonacion? {
        if (!exists()) return null
        val tipoString = getString("tipoPublicacion") ?: TipoPublicacion.SOLICITUD_SANGRE.name
        return SolicitudDonacion(
            id = id,
            institucion = getString("institucion") ?: return null,
            // La cuenta médica queda "en revisión", así que todavía no se marca como verificada.
            institucionVerificada = false,
            zona = getString("zona").orEmpty(),
            tipoSangre = getString("tipoSangre") ?: return null,
            tipoPublicacion = TipoPublicacion.valueOf(tipoString),
            urgencia = NivelUrgencia.URGENTE,
            // No calculamos distancia real; 0.0 significa "sin dato" y la pantalla no la muestra.
            distanciaKm = 0.0,
            vigenciaTexto = "Solicitud activa",
            direccion = getString("direccion").orEmpty(),
            horario = "Consulta con la institución",
            telefonoInstitucion = "No disponible",
            estaCerrada = getString(CAMPO_ESTADO) != EstadoSolicitud.ACTIVA.name,
        )
    }

    private fun DocumentSnapshot.aSolicitudCreada(): SolicitudCreada? {
        return SolicitudCreada(
            id = id,
            nombre = getString("nombrePaciente") ?: return null,
            ubicacion = getString("zona").orEmpty(),
            tipoSangre = getString("tipoSangre") ?: return null,
        )
    }

    private companion object {
        const val COLECCION = "solicitudes"
        const val CAMPO_ESTADO = "estado"
        const val CAMPO_MEDICA = "medicaUid"
        const val TIEMPO_ESPERA_MS = 8_000L
    }
}
