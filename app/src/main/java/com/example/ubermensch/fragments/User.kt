package com.example.ubermensch.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.ubermensch.R
import com.example.ubermensch.activities.ChangePasswordActivity
import com.example.ubermensch.activities.LogInActivity
import com.example.ubermensch.models.Habit
import com.example.ubermensch.repositories.ExperienceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.Console
import java.lang.Math.floor
import java.lang.Math.pow
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.roundToInt


class User : Fragment() {
    val user = FirebaseAuth.getInstance()
    private lateinit var displayName:TextView
    private lateinit var progress:ProgressBar
    private lateinit var level:TextView
    private lateinit var btnLogout: Button
    private lateinit var btnChangePass:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayName = view.findViewById(R.id.username)
        progress = view.findViewById(R.id.userLevelProgress)
        level = view.findViewById(R.id.userLevel)
        btnLogout = view.findViewById(R.id.buttonLogout)
        btnChangePass = view.findViewById(R.id.buttonChangePassword)

        if(user != null) //TODO:pogledati da li je ovo dobro
        {
            displayName.text = user.currentUser?.email.toString().subSequence(0,user.currentUser?.email.toString().indexOf("@"))
        }
        btnLogout.setOnClickListener{
            user.signOut()
            startActivity(Intent(activity,LogInActivity::class.java))
            try{
                activity?.finish()

            }catch( ex:Exception){
                Toast.makeText(activity,"Something went wrong! Try again later!",Toast.LENGTH_SHORT).show()

            }

        }
        btnChangePass.setOnClickListener {
            startActivity(Intent(activity,ChangePasswordActivity::class.java))
            activity?.finish()
        }



        //TODO:Prebaciti ovu logiku u experience repository
        ExperienceRepository.databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                level.text = snapshot.child("Level").getValue().toString()+" lvl."

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        ExperienceRepository.databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var xp = snapshot.child("XP").getValue().toString().toDouble()
                //max level is calculated based on exp required for the next level - 1 since that
                //value is the last one in current lvl
                var max = ((level.text.toString().substringBefore(" lvl.").toInt()+1)/0.07).pow(2).toInt()-1
                progress.setMax(max-xp.toInt())
                progress.setProgress(xp.toInt())
                ExperienceRepository.updateLVL(xp)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}
