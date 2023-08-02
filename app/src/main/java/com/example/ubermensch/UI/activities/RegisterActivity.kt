package com.example.ubermensch.UI.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ubermensch.databinding.ActivityRegisterBinding
import com.example.ubermensch.DataLayer.repositories.ExperienceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogIn.setOnClickListener {
            val email = binding.usersEmail.text.toString()
            val pass = binding.usersPassword.text.toString()
            val confirmPass = binding.confirmPassword.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass)
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if(it.isSuccessful){
                            val database: DatabaseReference =
                                FirebaseDatabase.getInstance().getReference("Experience")
                            val d = database.child(firebaseAuth.currentUser?.uid.toString())
                            d.child("Level").setValue(1)
                            d.child("XP").setValue(0)
                            val intent = Intent(this, LogInActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                        else{
                            Toast.makeText(this,"Error while creating a user! Try again!",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this,"Password is not matching",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText( this,"Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imageBack.setOnClickListener{
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}