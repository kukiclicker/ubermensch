package com.example.ubermensch.DomainLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ubermensch.DataLayer.models.ToDo
import com.example.ubermensch.DataLayer.repositories.TodoRepository

class TodoViewModel :ViewModel() {
    private val repository : TodoRepository
    private val _allTodos = MutableLiveData<List<ToDo>>()
    val allTodos : LiveData<List<ToDo>> = _allTodos


    init {

        repository = TodoRepository().getInstance()
        repository.loadTodos(_allTodos)

    }
}