package com.example.ubermensch.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LoginUtilTest{
    @Test
    fun testEmptyEmail() {
        val result = LoginUtil.userInputValidation(
            "",
            "password",
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testValidRegister() {
        val result = LoginUtil.userInputValidation(
            "umitrovic@gmail.com",
            "1234",
        )
        assertThat(result).isTrue()
    }
    @Test
    fun testEmptyPassword() {
        val result = LoginUtil.userInputValidation(
            "umitrovic22@gmail.com",
            "",
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testLengthOfPassword() {
        val result = LoginUtil.userInputValidation(
            "petarpetrovic@gmail.com",
            "a",
        )
        assertThat(result).isFalse()
    }
}