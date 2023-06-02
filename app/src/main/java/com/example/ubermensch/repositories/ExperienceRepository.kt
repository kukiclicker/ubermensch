package com.example.ubermensch.repositories

import androidx.lifecycle.MutableLiveData
import com.example.ubermensch.models.Experience
import com.example.ubermensch.models.Habit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.Math.floor
import java.lang.Math.sqrt

class ExperienceRepository {
    companion object{
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Experience").child(
                FirebaseAuth.getInstance().currentUser?.uid
                    ?: "Error! UID "
            )
        fun updateLVL(xp:Double){
            databaseReference.child("Level").setValue(floor(0.07* kotlin.math.sqrt(xp)))
        }

    }
    @Volatile
    private var INSTANCE: ExperienceRepository? = null
    fun getInstance(): ExperienceRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = ExperienceRepository()
            INSTANCE = instance
            instance
        }
    }
}
