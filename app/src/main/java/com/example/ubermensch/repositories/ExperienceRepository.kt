package com.example.ubermensch.repositories

import androidx.lifecycle.MutableLiveData
import com.example.ubermensch.models.Experience
import com.example.ubermensch.models.Habit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ExperienceRepository {
    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Experience").child(
            FirebaseAuth.getInstance().currentUser?.uid
                ?: "Error! UID "
        )
    @Volatile
    private var INSTANCE: ExperienceRepository? = null
    fun getInstance(): ExperienceRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = ExperienceRepository()
            INSTANCE = instance
            instance
        }
    }


    /*fun addNewUserStats(newUserUID:String): Boolean {
        if(newUserUID=="")
            return false
        val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Experience").child(
            newUserUID)
        database.child("Level").setValue(1)
        database.child("XP").setValue(0)
        return true
    }*/
}
