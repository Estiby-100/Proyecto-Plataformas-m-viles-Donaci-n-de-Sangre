package com.uvg.gotavida.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.uvg.gotavida.data.fake.FakeHospitalesDataSource
import com.uvg.gotavida.data.model.Institucion
import com.uvg.gotavida.data.model.TipoInstitucion
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Hospitales y bancos de sangre guardados en Cloud Firestore (colección
 * "instituciones"). Se usan para autocompletar la institución en el registro
 * de la médica y para obtener la dirección al crear una solicitud.
 *
 * La lista inicial (FakeHospitalesDataSource) solo se usa para sembrar la
 * colección la primera vez y como respaldo si no hay internet.
 */
class InstitucionesRepository {

    private val coleccion by lazy { FirebaseFirestore.getInstance().collection(COLECCION) }

    /** Instituciones de Firestore en tiempo real, ordenadas por nombre. */
    fun observar(): Flow<List<Institucion>> = callbackFlow {
        val registro = coleccion.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val lista = snapshot?.documents.orEmpty()
                .mapNotNull { it.aInstitucion() }
                .sortedBy { it.nombre }
            trySend(lista)
        }
        awaitClose { registro.remove() }
    }

    /**
     * Sube la lista inicial si la colección está vacía. Es segura de llamar
     * varias veces: si ya hay datos, no hace nada. Si falla (sin internet,
     * sin sesión) se ignora; la app sigue con la lista de respaldo.
     */
    suspend fun sembrarSiEstaVacia() {
        runCatching {
            withTimeoutOrNull(TIEMPO_ESPERA_MS) {
                val existentes = coleccion.limit(1).get().await()
                if (existentes.isEmpty) {
                    val lote = FirebaseFirestore.getInstance().batch()
                    FakeHospitalesDataSource.instituciones.forEach { inst ->
                        lote.set(
                            coleccion.document(inst.id),
                            mapOf(
                                "nombre" to inst.nombre,
                                "ubicacion" to inst.ubicacion,
                                "tipo" to inst.tipo.name,
                            ),
                        )
                    }
                    lote.commit().await()
                }
            }
        }
    }

    /** Busca una institución por nombre en Firestore; si falla, usa la lista de respaldo. */
    suspend fun buscarPorNombre(nombre: String): Institucion? {
        val texto = nombre.trim()
        if (texto.isEmpty()) return null
        val remotas = runCatching {
            withTimeoutOrNull(TIEMPO_ESPERA_MS) { coleccion.get().await() }
                ?.documents.orEmpty()
                .mapNotNull { it.aInstitucion() }
        }.getOrDefault(emptyList())
        return remotas.firstOrNull { it.nombre.equals(texto, ignoreCase = true) }
            ?: remotas.firstOrNull { it.nombre.contains(texto, ignoreCase = true) }
            ?: FakeHospitalesDataSource.buscar(texto).firstOrNull()
    }

    private fun DocumentSnapshot.aInstitucion(): Institucion? {
        val tipo = runCatching { TipoInstitucion.valueOf(getString("tipo").orEmpty()) }
            .getOrDefault(TipoInstitucion.HOSPITAL)
        return Institucion(
            id = id,
            nombre = getString("nombre") ?: return null,
            ubicacion = getString("ubicacion").orEmpty(),
            tipo = tipo,
        )
    }

    private companion object {
        const val COLECCION = "instituciones"
        const val TIEMPO_ESPERA_MS = 8_000L
    }
}
