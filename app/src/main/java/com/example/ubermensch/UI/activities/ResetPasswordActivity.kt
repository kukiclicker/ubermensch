package com.example.ubermensch.UI.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ubermensch.R
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var btnReset: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        email = findViewById(R.id.resetEmail)
        btnReset = findViewById(R.id.buttonReset)
        auth = FirebaseAuth.getInstance()
        btnReset.setOnClickListener{
            var sPassword = email.text.toString()
            auth.sendPasswordResetEmail(sPassword).addOnSuccessListener {
                Toast.makeText(this,"Request for password reset was sent to your email!",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }

        }
    }
}