package com.example.ubermensch.UI.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.ubermensch.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var buttonRestart:Button
    private lateinit var currentPassword:TextView
    private lateinit var newPassword:TextView
    private lateinit var confirmPassword:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        buttonRestart = findViewById(R.id.btnRestartPassword)
        currentPassword = findViewById(R.id.currentPassword)
        newPassword = findViewById(R.id.newPassword)
        confirmPassword = findViewById(R.id.confirmNewPassword)
        auth = FirebaseAuth.getInstance()
        buttonRestart.setOnClickListener{
            changePassword()
        }

    }
    private fun changePassword() {
        if(currentPassword.text.isNotEmpty() && newPassword.text.isNotEmpty() && confirmPassword.text.isNotEmpty()){

            if(newPassword.text.toString().equals(confirmPassword.text.toString())){
                val user = auth.currentUser
                if(user != null && user.email != null){
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currentPassword.text.toString())

                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if(it.isSuccessful)
                            {
                                Toast.makeText(this,"Re-authentication success!",Toast.LENGTH_SHORT).show()
                                user!!.updatePassword(newPassword.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful && !currentPassword.equals(newPassword)) {
                                            Toast.makeText(this,"Password changed successfully!",Toast.LENGTH_SHORT).show()
                                            auth.signOut()
                                            startActivity(Intent(this, LogInActivity::class.java))
                                            finish()
                                        }
                                    }
                            }
                            else{
                                Toast.makeText(this,"Re-authentication fail!",Toast.LENGTH_SHORT).show()

                            }
                        }
                }
            }
            else{
                Toast.makeText(this,"Password doesn't match!",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"Please enter all the fields!",Toast.LENGTH_SHORT).show()
        }
    }
}