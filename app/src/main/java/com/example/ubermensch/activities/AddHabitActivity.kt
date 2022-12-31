package com.example.ubermensch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ubermensch.R
import com.example.ubermensch.models.Habit
import com.example.ubermensch.repositories.HabitRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Date

class AddHabitActivity : AppCompatActivity() {

    private lateinit var t_title: EditText
    private lateinit var t_note: EditText
    private lateinit var  t_tag: EditText
    private lateinit var t_date: EditText
    private lateinit var t_difficulty: EditText
    private lateinit var btnAdd:Button
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        t_title = findViewById(R.id.title)
        t_note = findViewById(R.id.note)
        t_tag = findViewById(R.id.tag)
        t_date = findViewById(R.id.date)
        t_difficulty = findViewById(R.id.difficulty)
        btnAdd = findViewById(R.id.btnAddHabit)

        dbRef = HabitRepository().databaseReference

        btnAdd.setOnClickListener{
            addHabit()
        }
    }
    private fun addHabit(){
        val habit_title = t_title.text.toString()
        val note = t_note.text.toString()
        val tag = t_tag.text.toString()
        val date = t_date.text.toString()
        val difficulty = t_difficulty.text.toString()

        if(habit_title.isEmpty()){
            t_title.error = "Please enter habit title"
        }
        if(note.isEmpty()){
            t_note.error = "Please enter habit note"
        }
        if(tag.isEmpty()){
            t_tag.error = "Please enter tag for the habit"
        }
        if(date.isEmpty()){
            t_date.error = "Please enter date of habit"
        }
        if(difficulty.isEmpty()){
            t_difficulty.error = "Please enter habit difficulty"
        }

        val habitID = dbRef.push().key!!
        val habit = Habit(habit_title,note,date,difficulty,tag,false)
        dbRef.child(habitID).setValue(habit).addOnCompleteListener {
            Toast.makeText(this,"Habit added succesfully",Toast.LENGTH_LONG).show()
            t_title.text.clear()
            t_note.text.clear()
            t_tag.text.clear()
            t_date.text.clear()
            t_difficulty.text.clear()
        }.addOnFailureListener{
            err -> Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
        }
    }

}