package com.example.ubermensch.DataLayer.repositories

import androidx.lifecycle.MutableLiveData
import com.example.ubermensch.DataLayer.models.ToDo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TodoRepository {
    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("ToDos").child(
            FirebaseAuth.getInstance().currentUser?.uid
            ?: "Error! UID ")

    @Volatile
    private var INSTANCE: TodoRepository? = null

    fun getInstance(): TodoRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = TodoRepository()
            INSTANCE = instance
            instance
        }
    }
    fun loadTodos(todoList: MutableLiveData<List<ToDo>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _todoList: List<ToDo> = snapshot.children.map { dataSnapshot ->
                        val text = dataSnapshot.child("text").getValue().toString()
                        ToDo(text)
                    }
                    todoList.postValue(_todoList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}