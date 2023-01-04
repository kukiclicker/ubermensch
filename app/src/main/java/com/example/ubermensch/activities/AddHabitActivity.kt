package com.example.ubermensch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ubermensch.R
import com.example.ubermensch.models.Habit
import com.example.ubermensch.repositories.HabitRepository
import com.google.firebase.database.DatabaseReference


class AddHabitActivity : AppCompatActivity() {

    private lateinit var t_title: EditText
    private lateinit var t_note: EditText
    private lateinit var  t_tag: EditText
    private lateinit var t_date: TextView
    private lateinit var t_difficulty: EditText
    private lateinit var btnAdd:Button
    private lateinit var dbRef:DatabaseReference
    private lateinit var dP: DatePicker
    private lateinit var l_title: TextView
    private lateinit var l_note: TextView
    private lateinit var l_tag: TextView
    private lateinit var l_date: TextView
    private lateinit var btnConfirm: Button
    private lateinit var l_difficulty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        t_title = findViewById(R.id.title)
        t_note = findViewById(R.id.note)
        t_tag = findViewById(R.id.tag)
        t_date = findViewById(R.id.date)
        t_difficulty = findViewById(R.id.difficulty)
        btnAdd = findViewById(R.id.btnAddHabit)
        dP = findViewById(R.id.datePicker)
        l_title = findViewById(R.id.textView3)
        l_note = findViewById(R.id.textView4)
        l_tag = findViewById(R.id.textView6)
        l_date = findViewById(R.id.textView7)
        l_difficulty = findViewById(R.id.textView5)
        btnConfirm = findViewById(R.id.btnConfirmDate)
        dbRef = HabitRepository().databaseReference

        t_date.setOnClickListener{
            pickDate()
        }
        btnAdd.setOnClickListener{
            addHabit()
        }
    }
    private fun pickDate(){
        t_title.visibility = View.INVISIBLE
        t_note.visibility = View.INVISIBLE
        t_tag.visibility = View.INVISIBLE
        t_date.visibility = View.INVISIBLE
        t_difficulty.visibility = View.INVISIBLE
        btnAdd.visibility = View.INVISIBLE
        dP.visibility = View.VISIBLE
        l_date.visibility = View.INVISIBLE
        l_difficulty.visibility = View.INVISIBLE
        l_tag.visibility = View.INVISIBLE
        l_title.visibility = View.INVISIBLE
        l_note.visibility = View.INVISIBLE
        btnConfirm.visibility = View.VISIBLE
        btnConfirm.setOnClickListener {
            t_title.visibility = View.VISIBLE
            t_note.visibility = View.VISIBLE
            t_tag.visibility = View.VISIBLE
            t_date.visibility = View.VISIBLE
            t_difficulty.visibility = View.VISIBLE
            btnAdd.visibility = View.VISIBLE
            l_date.visibility = View.VISIBLE
            l_difficulty.visibility = View.VISIBLE
            l_tag.visibility = View.VISIBLE
            l_title.visibility = View.VISIBLE
            l_note.visibility = View.VISIBLE
            dP.visibility = View.INVISIBLE
            btnConfirm.visibility = View.INVISIBLE
            t_date.text = dP.dayOfMonth.toString()+"/"+(dP.month.toInt()+1).toString()+"/"+dP.year.toString()
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
        else{
            val habitID = dbRef.push().key!!
            val habit = Habit(habit_title,note,date,difficulty,tag,false)
            dbRef.child(habitID).setValue(habit).addOnCompleteListener {
                Toast.makeText(this,"Habit added successfully",Toast.LENGTH_LONG).show()
                t_title.text.clear()
                t_note.text.clear()
                t_tag.text.clear()
                t_date.text = ""
                t_difficulty.text.clear()
            }.addOnFailureListener{
                    err -> Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
            }
        }

    }

}