package com.uvg.gotavida.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Operaciones directas en la tabla de usuarios locales.
@Dao
interface UsuarioDao {

    // Guarda o sobreescribe los datos del perfil
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(usuario: UsuarioEntity)

    // Busca un usuario por su UID de Firebase
    @Query("SELECT * FROM usuarios WHERE uid = :uid LIMIT 1")
    suspend fun obtenerPorUid(uid: String): UsuarioEntity?

    // Escucha cambios en el perfil del usuario activo
    @Query("SELECT * FROM usuarios WHERE uid = :uid LIMIT 1")
    fun observarPorUid(uid: String): Flow<UsuarioEntity?>

    // Elimina todos los registros de usuarios guardados
    @Query("DELETE FROM usuarios")
    suspend fun borrarTodo()
}
