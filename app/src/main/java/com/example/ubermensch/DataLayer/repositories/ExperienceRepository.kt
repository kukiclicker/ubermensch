package com.example.ubermensch.DataLayer.repositories

import androidx.lifecycle.MutableLiveData
import com.example.ubermensch.DataLayer.models.Experience
import com.example.ubermensch.DataLayer.models.Habit
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
        fun updateXP(xp:Double){

            val query =
                databaseReference.child("XP").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                       snapshot.ref.setValue(snapshot.getValue().toString().toDouble()+xp)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

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
