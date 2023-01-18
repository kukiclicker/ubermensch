package com.example.ubermensch.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AddHabitUtilTest{
    @Test
    fun testEmptyTitle(){
        var result = AddHabitUtil.addHabitValidation(
            "",
            "clean room",
            "chores",
            "12/12/2022",
            "easy"
        )
        assertThat(result).isFalse()

    }
    @Test
    fun testEmptyNote(){
        var result = AddHabitUtil.addHabitValidation(
            "Cleaning",
            "",
            "chores",
            "12/12/2022",
            "easy"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testEmptyTag(){
        var result = AddHabitUtil.addHabitValidation(
            "Cleaning",
            "clean room",
            "",
            "12/12/2022",
            "easy"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testEmptyDate(){
        var result = AddHabitUtil.addHabitValidation(
            "Cleaning",
            "clean room",
            "chores",
            "",
            "easy"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testEmptyDifficulty(){
        var result = AddHabitUtil.addHabitValidation(
            "Cleaning",
            "clean room",
            "chores",
            "12/12/2022",
            ""
        )
        assertThat(result).isFalse()
    }
    @Test
    fun testValidAddition(){
        var result = AddHabitUtil.addHabitValidation(
            "Cleaning",
            "clean room",
            "chores",
            "12/12/2022",
            "easy"
        )
        assertThat(result).isTrue()
    }
    @Test
    fun testNoData(){
        var result = AddHabitUtil.addHabitValidation(
            "",
            "",
            "",
            "",
            ""
        )
        assertThat(result).isFalse()
    }


}