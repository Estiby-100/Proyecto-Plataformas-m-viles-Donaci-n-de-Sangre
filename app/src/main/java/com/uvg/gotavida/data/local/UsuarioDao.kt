package com.uvg.gotavida.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE uid = :uid LIMIT 1")
    suspend fun obtenerPorUid(uid: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE uid = :uid LIMIT 1")
    fun observarPorUid(uid: String): Flow<UsuarioEntity?>

    @Query("DELETE FROM usuarios")
    suspend fun borrarTodo()
}
