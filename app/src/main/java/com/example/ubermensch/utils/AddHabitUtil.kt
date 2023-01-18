package com.example.ubermensch.utils

object AddHabitUtil {
    fun addHabitValidation(
        title:String,
        note:String,
        tag:String,
        date:String,
        difficulty:String
    ):Boolean{
        if(title.isEmpty() || note.isEmpty() || tag.isEmpty() || date.isEmpty() || difficulty.isEmpty())
        {
            return false
        }
        return true
    }

}