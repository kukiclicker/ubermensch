package com.example.ubermensch.utils


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest{

    @Test
    fun testEmptyEmail(){
        val result = RegistrationUtil.userInputValidation(
            "",
            "password",
            "password"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testValidEmail(){
        val result = RegistrationUtil.userInputValidation(
            "umitrovic22@gmail.com",
            "password",
            "password"
        )
        assertThat(result).isTrue()
    }
    @Test
    fun testEmptyPassword(){
        val result = RegistrationUtil.userInputValidation(
            "name@gmail.com",
            "",
            "password"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testConfirmPasswordCorrectly(){
        val result = RegistrationUtil.userInputValidation(
            "name@gmail.com",
            "password",
            "password"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testConfirmPasswordIncorrectly(){
        val result = RegistrationUtil.userInputValidation(
            "name@gmail.com",
            "password",
            "password"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testPasswordWithLessThen4Digits(){
        val result = RegistrationUtil.userInputValidation(
            "name@gmail.com",
            "pas",
            "pas"
        )
        assertThat(result).isFalse()
    }
}