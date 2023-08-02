package com.example.ubermensch.DataLayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ubermensch.R
import com.example.ubermensch.DataLayer.models.ToDo
import com.example.ubermensch.DataLayer.repositories.ExperienceRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class TodoAdapter(): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    private val todoList = ArrayList<ToDo>()
    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("ToDos").child(
            FirebaseAuth.getInstance().currentUser?.uid
                ?: "Error! UID ")
    private lateinit var btnCompleteTask: FloatingActionButton

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_layout,
            parent,false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = todoList.get(position)
        holder.text.text = currentItem.text
        btnCompleteTask = holder.itemView.findViewById(R.id.fabTodoDone)
        btnCompleteTask.setOnClickListener{
            val query = databaseReference.orderByChild("text").equalTo(currentItem.text.toString())
            query.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (itemSnapshot in snapshot.children) {
                        val item = itemSnapshot.getValue(ToDo::class.java)
                        if (item != null) {
                            itemSnapshot.ref.removeValue()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            var xp:Double = 100.0
            ExperienceRepository.updateXP(xp)
        }

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