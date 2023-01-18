package com.example.ubermensch.utils

import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

object RegistrationUtil {

    private val usersEmail = listOf("umitrovic22@gmail.com")

    fun userInputValidation(
        email:String,
        password:String,
        confirmedPassword:String
    ):Boolean{
        if(email.isEmpty() || password.isEmpty()) {
            return false
        }
        else if(password != confirmedPassword) {
            return false
        }
        if(password.count { it.isDigit() } < 2) {
            return false
        }
        if(usersEmail.contains(email))
        {
            return false
        }
        return true
    }
}