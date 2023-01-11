package com.example.ubermensch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ubermensch.R
import com.example.ubermensch.models.ToDo
import com.example.ubermensch.repositories.TodoRepository
import com.google.firebase.database.DatabaseReference

private lateinit var dbRef: DatabaseReference
private lateinit var addTodo: Button
private lateinit var todoText: EditText

class AddTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        addTodo = findViewById(R.id.btnAddTodo)
        todoText = findViewById(R.id.newTodoText)
        addTodo.setOnClickListener{
            addTodo()
        }
        dbRef = TodoRepository().databaseReference
    }
    private fun addTodo(){
        val text = todoText.text.toString()
        if(text.isEmpty()){
            todoText.error = "Please enter todo text"
        }
        else{
            val todoID = dbRef.push().key!!
            val todo = ToDo(text)
            dbRef.child(todoID).setValue(todo).addOnCompleteListener {
                Toast.makeText(this,"To-do added successfully", Toast.LENGTH_LONG).show()
                todoText.text.clear()

            }.addOnFailureListener{
                    err -> Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}