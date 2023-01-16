package com.example.ubermensch.utils

import org.junit.Assert.*
import org.junit.Test

class RegistrationUtilTest{

    @Test
    fun testEmptyUsername(){
        val result = RegistrationUtil.userInputValidation(
            "",
            "password",
            "password"
        )

    }
}