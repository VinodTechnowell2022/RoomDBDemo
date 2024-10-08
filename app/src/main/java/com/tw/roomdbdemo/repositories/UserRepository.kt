package com.tw.roomdbdemo.repositories

import androidx.lifecycle.LiveData
import com.tw.roomdbdemo.daos.UserDao
import com.tw.roomdbdemo.models.UserTable

class UserRepository(private val toDoDao: UserDao) {

    val getAllData: LiveData<List<UserTable>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: UserTable){
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: UserTable){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: UserTable){
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<UserTable>> {
        return toDoDao.searchDatabase(searchQuery)
    }

    fun searchNoDuplicateOnNameBased(): LiveData<List<UserTable>> {
        return toDoDao.searchNoDuplicateOnNameBased()
    }

    fun searchOnNameAndPhone(searchQuery: String, phone: String): LiveData<List<UserTable>> {
        return toDoDao.searchOnNameAndPhone(searchQuery, phone)
    }

    fun searchOnNameAndEmailAndPhoneList(name: String, phoneList: List<String>): LiveData<List<UserTable>> {
        return toDoDao.searchOnNameAndEmailAndPhoneList(name, phoneList)
    }

    fun getAllUsersOlderThan(minAge:Int): LiveData<List<UserTable>> {
        return toDoDao.getAllUsersOlderThan(minAge)
    }

    fun getAllUsersBetweenAge(minAge: Int, maxAge:Int): LiveData<List<UserTable>> {
        return toDoDao.getAllUsersBetweenAge(minAge, maxAge)
    }



}