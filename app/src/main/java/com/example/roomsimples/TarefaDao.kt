package com.example.roomsimples

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {
    @Insert
    suspend fun inserir(tarefa: Tarefa)

    @Update
    suspend fun atualizar(tarefa: Tarefa)

    @Delete
    suspend fun deletar(tarefa: Tarefa)

    @Query("SELECT * FROM tarefas_table ORDER BY id DESC")
    fun buscarTodas(): Flow<List<Tarefa>>

    @Query("DELETE FROM tarefas_table")
    suspend fun apagarTudo()
}
