package com.example.ubermensch.utils


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest{

    @Test
    fun testEmptyEmail() {
        val result = RegistrationUtil.userInputValidation(
            "",
            "password",
            "password"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun testValidRegister() {
        val result = RegistrationUtil.userInputValidation(
            "umitrovic22@gmail.com",
            "1234",
            "1234"
        )
        assertThat(result).isTrue()
    }
    @Test
    fun testIncorrectConfirmedPassword() {
        val result = RegistrationUtil.userInputValidation(
            "umitrovic22@gmail.com",
            "123456",
            "abcdefg"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun testEmptyPassword() {
        val result = RegistrationUtil.userInputValidation(
            "umitrovic22@gmail.com",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun testLengthOfPassword() {
        val result = RegistrationUtil.userInputValidation(
            "umitrovic22@gmail.com",
            "abcdefg5",
            "abcdefg5"
        )
        assertThat(result).isFalse()
    }
}