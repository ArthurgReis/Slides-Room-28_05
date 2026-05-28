package com.example.roomsimples

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefas_table")
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val descricao: String,
    val estaConcluida: Boolean = false
)