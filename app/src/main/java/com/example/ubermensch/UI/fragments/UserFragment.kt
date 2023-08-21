package com.example.ubermensch.UI.fragments

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
import com.example.ubermensch.UI.activities.ChangePasswordActivity
import com.example.ubermensch.UI.activities.LogInActivity
import com.example.ubermensch.DataLayer.repositories.ExperienceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.math.pow


class UserFragment : Fragment() {
    val user = FirebaseAuth.getInstance()
    private lateinit var displayName:TextView
    private lateinit var progress:ProgressBar
    private lateinit var level:TextView
    private lateinit var btnLogout: Button
    private lateinit var btnChangePass:Button
    val X_CONST = 0.07
    val Y_CONST = 2

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
            startActivity(Intent(activity, LogInActivity::class.java))
            try{
                activity?.finish()

            }catch( ex:Exception){
                Toast.makeText(activity,"Something went wrong! Try again later!",Toast.LENGTH_SHORT).show()

            }

        }
        btnChangePass.setOnClickListener {
            startActivity(Intent(activity, ChangePasswordActivity::class.java))
            activity?.finish()
        }

        //TODO:Prebaciti ovu logiku u experience repository
        ExperienceRepository.databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                level.text = snapshot.child("Level").getValue().toString()+" lvl."

                var xp = snapshot.child("XP").getValue().toString().toDouble() //reading value from db
                var lvl = level.text.toString().substringBefore(" lvl.").toInt()
                var xp_points = xp.toInt()
                //calculating current level experience using same formula -> (level/X)^Y for a min value
                var current_lvl_xp = (lvl/X_CONST).pow(Y_CONST).toInt()
                //max value exp uses same formula with difference in level, now being level+1
                var max = ((lvl+1)/X_CONST).pow(Y_CONST).toInt()-1
                //max of progress bar is difference between max exp and current level xp
                progress.setMax(max-current_lvl_xp)
                //progress number of exp should be how any exp you have earned since passing last lvl
                progress.setProgress(xp_points-current_lvl_xp)

                //updating level in database based on exp change
                ExperienceRepository.updateLVL(xp)

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
