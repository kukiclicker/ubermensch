package com.example.ubermensch.repositories

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.ubermensch.models.Habit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HabitRepository {
    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Habits").child(FirebaseAuth.getInstance().currentUser?.uid
            ?: "Error! UID ")

    @Volatile
    private var INSTANCE: HabitRepository? = null

    fun getInstance(): HabitRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = HabitRepository()
            INSTANCE = instance
            instance
        }
    }
    fun loadHabits(habitList: MutableLiveData<List<Habit>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _habitList: List<Habit> = snapshot.children.map { dataSnapshot ->

                        val title = dataSnapshot.child("title").getValue().toString()
                        val note = dataSnapshot.child("note").getValue().toString()
                        val date = dataSnapshot.child("date").getValue().toString()
                        val difficulty = dataSnapshot.child("difficulty").getValue().toString()
                        val tag = dataSnapshot.child("tag").getValue().toString()
                        val isDone = dataSnapshot.child("isDone").getValue().toString().toBoolean()

                        Habit(title, note,date,difficulty,tag,isDone)!!
                    }
                    habitList.postValue(_habitList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

}