package com.example.ubermensch.utils

object LoginUtil {
    fun userInputValidation(
        email:String,
        password:String
    ):Boolean{
        if(email.isEmpty() || password.isEmpty()) {
            return false
        }
        if(password.count { it.isDigit() } < 2) {
            return false
        }
        return true
    }
}