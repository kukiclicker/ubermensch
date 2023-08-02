package com.example.ubermensch.DomainLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ubermensch.DataLayer.models.Habit
import com.example.ubermensch.DataLayer.repositories.HabitRepository


class HabitViewModel : ViewModel() {
    private val repository : HabitRepository
    private val _allHabits = MutableLiveData<List<Habit>>()
    val allHabits : LiveData<List<Habit>> = _allHabits

    init {
        repository = HabitRepository().getInstance()
        repository.loadHabits(_allHabits)
    }
}