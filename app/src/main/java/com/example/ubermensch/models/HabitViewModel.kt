package com.example.ubermensch.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ubermensch.repositories.HabitRepository


class HabitViewModel : ViewModel() {
    private val repository : HabitRepository
    private val _allHabits = MutableLiveData<List<Habit>>()
    val allHabits : LiveData<List<Habit>> = _allHabits

    init {
        repository = HabitRepository().getInstance()
        repository.loadHabits(_allHabits)
    }
}