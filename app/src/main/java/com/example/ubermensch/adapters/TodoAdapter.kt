package com.example.ubermensch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ubermensch.R
import com.example.ubermensch.models.Habit
import com.example.ubermensch.models.ToDo
import com.example.ubermensch.models.TodoViewModel
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
            R.layout.habit_layout,
            parent,false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HabitAdapter.HabitViewHolder, position: Int) {
        val currentItem = habitList.get(position)
        holder.title.text = currentItem.title
        holder.note.text = currentItem.note
        holder.date.text = currentItem.date
        holder.difficulty.text = currentItem.difficulty
        holder.tag.text = currentItem.tag
        holder.counter.text = currentItem.counter.toString()
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


    }



}