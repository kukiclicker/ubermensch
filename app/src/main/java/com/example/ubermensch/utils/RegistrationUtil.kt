package com.example.ubermensch.utils

import com.google.firebase.auth.FirebaseAuth

object RegistrationUtil {
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
        return true
    }
}