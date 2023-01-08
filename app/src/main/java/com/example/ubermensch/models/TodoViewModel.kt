package com.example.ubermensch.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ubermensch.repositories.TodoRepository

class TodoViewModel :ViewModel() {
    private val repository : TodoRepository
    private val _allTodos = MutableLiveData<List<ToDo>>()
    val allTodos : LiveData<List<ToDo>> = _allTodos


    init {

        repository = TodoRepository().getInstance()
        repository.loadTodos(_allTodos)

    }
}