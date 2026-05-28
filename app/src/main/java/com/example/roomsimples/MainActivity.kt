package com.example.roomsimples

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var tarefaDao: TarefaDao
    private lateinit var editTextTarefa: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnLimpar: Button
    private lateinit var recyclerViewTarefas: RecyclerView
    private lateinit var adapter: TarefaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getDatabase(this)
        tarefaDao = database.tarefaDao()

        editTextTarefa = findViewById(R.id.editTextTarefa)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnLimpar = findViewById(R.id.btnLimpar)
        recyclerViewTarefas = findViewById(R.id.recyclerViewTarefas)

        configurarRecyclerView()

        atualizarListaDeTarefas()

        btnSalvar.setOnClickListener {
            val descricao = editTextTarefa.text.toString().trim()
            if (descricao.isNotEmpty()) {
                val novaTarefa = Tarefa(descricao = descricao)
                lifecycleScope.launch {
                    tarefaDao.inserir(novaTarefa)
                    editTextTarefa.text.clear()
                }
            }
        }

        btnLimpar.setOnClickListener {
            lifecycleScope.launch { tarefaDao.apagarTudo() }
        }
    }

    private fun configurarRecyclerView() {
        adapter = TarefaAdapter(
            onTarefaCheckChanged = { tarefa, isChecked ->
                val tarefaAtualizada = tarefa.copy(estaConcluida = isChecked)
                lifecycleScope.launch {
                    tarefaDao.atualizar(tarefaAtualizada)
                }
            },
            onTarefaDeletada = { tarefa ->
                lifecycleScope.launch {
                    tarefaDao.deletar(tarefa)
                    Toast.makeText(this@MainActivity, "Tarefa apagada!", Toast.LENGTH_SHORT).show()
                }
            }
        )

        recyclerViewTarefas.layoutManager = LinearLayoutManager(this)
        recyclerViewTarefas.adapter = adapter
    }

    private fun atualizarListaDeTarefas() {
        lifecycleScope.launch {
            tarefaDao.buscarTodas().collect { lista ->
                adapter.setTarefas(lista)
            }
        }
    }
}