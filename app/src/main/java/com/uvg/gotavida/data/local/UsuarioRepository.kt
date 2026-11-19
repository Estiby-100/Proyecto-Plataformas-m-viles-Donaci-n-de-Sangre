package com.uvg.gotavida.data.local

import kotlinx.coroutines.flow.Flow

// Acceso desacoplado al almacenamiento local del perfil de usuario.
class UsuarioRepository(private val dao: UsuarioDao) {

    // Almacena o actualiza la información del usuario
    suspend fun guardar(usuario: UsuarioEntity) = dao.guardar(usuario)

    // Recupera la información de un usuario específico
    suspend fun obtener(uid: String): UsuarioEntity? = dao.obtenerPorUid(uid)

    // Permite escuchar cambios en vivo sobre los datos del perfil
    fun observar(uid: String): Flow<UsuarioEntity?> = dao.observarPorUid(uid)

    // Borra la base de usuarios almacenados localmente
    suspend fun limpiar() = dao.borrarTodo()
}
