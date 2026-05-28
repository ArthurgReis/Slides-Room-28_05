package com.example.roomsimples

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TarefaAdapter(
    private val onTarefaCheckChanged: (Tarefa, Boolean) -> Unit,
    private val onTarefaDeletada: (Tarefa) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var tarefas = emptyList<Tarefa>()

    fun setTarefas(novasTarefas: List<Tarefa>) {
        this.tarefas = novasTarefas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefaCorrente = tarefas[position]

        holder.textViewDescricao.text = tarefaCorrente.descricao

        holder.checkBoxConcluida.setOnCheckedChangeListener(null)
        holder.checkBoxConcluida.isChecked = tarefaCorrente.estaConcluida

        holder.checkBoxConcluida.setOnCheckedChangeListener { _, isChecked ->
            onTarefaCheckChanged(tarefaCorrente, isChecked)
        }

        holder.btnDeletar.setOnClickListener {
            onTarefaDeletada(tarefaCorrente)
        }
    }

    override fun getItemCount(): Int = tarefas.size

    class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDescricao: TextView = itemView.findViewById(R.id.textViewDescricao)
        val checkBoxConcluida: CheckBox = itemView.findViewById(R.id.checkBoxConcluida)
        val btnDeletar: Button = itemView.findViewById(R.id.btnDeletar)
    }
}