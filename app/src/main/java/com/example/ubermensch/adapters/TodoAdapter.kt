package com.example.ubermensch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ubermensch.R
import com.example.ubermensch.models.ToDo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class TodoAdapter(): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    private val todoList = ArrayList<ToDo>()
    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("ToDos").child(
            FirebaseAuth.getInstance().currentUser?.uid
                ?: "Error! UID ")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_layout,
            parent,false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        val currentItem = todoList.get(position)
        holder.text.text = currentItem.text

    }
    fun updateTodos(todoList: ArrayList<ToDo>?){
        this.todoList. clear()
        if (todoList != null) {
            this.todoList.addAll(todoList)
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return todoList.size
    }
    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text:TextView = itemView.findViewById(R.id.todoText)

    }



}