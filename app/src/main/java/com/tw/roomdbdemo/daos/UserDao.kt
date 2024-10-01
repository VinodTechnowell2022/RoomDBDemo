package com.tw.roomdbdemo.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tw.roomdbdemo.models.UserTable

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<UserTable>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: UserTable)

    @Update
    suspend fun updateData(toDoData: UserTable)

    @Delete
    suspend fun deleteItem(toDoData: UserTable)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table WHERE name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<UserTable>>

}