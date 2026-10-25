package com.uvg.gotavida.data.local

import kotlinx.coroutines.flow.Flow

/**
 * Único punto de acceso a la base local de usuarios. Los ViewModels usan
 * esta clase y nunca el DAO directamente.
 */
class UsuarioRepository(private val dao: UsuarioDao) {

    suspend fun guardar(usuario: UsuarioEntity) = dao.guardar(usuario)

    suspend fun obtener(uid: String): UsuarioEntity? = dao.obtenerPorUid(uid)

    fun observar(uid: String): Flow<UsuarioEntity?> = dao.observarPorUid(uid)

    suspend fun limpiar() = dao.borrarTodo()
}
