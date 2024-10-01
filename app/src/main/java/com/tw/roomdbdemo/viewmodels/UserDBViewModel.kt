package com.tw.roomdbdemo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tw.roomdbdemo.roomDB.MyDatabase
import com.tw.roomdbdemo.repositories.UserRepository
import com.tw.roomdbdemo.models.UserTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDBViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = MyDatabase.getDatabase(application).userDao()
    private val repository: UserRepository = UserRepository(toDoDao)

    init {
        repository.getAllData // Potential NullPointerException if repository is null
    }

    val getAllData: LiveData<List<UserTable>> = repository.getAllData

    fun insertData(toDoData: UserTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: UserTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteItem(toDoData: UserTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<UserTable>>{
        return repository.searchDatabase(searchQuery)
    }

}