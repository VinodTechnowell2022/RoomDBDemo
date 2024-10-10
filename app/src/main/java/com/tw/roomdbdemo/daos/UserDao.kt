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

//    @Query("SELECT * FROM user_table WHERE name LIKE '%' || :searchQuery || '%'")
    @Query("SELECT * FROM user_table WHERE name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<UserTable>>

    @Query("Select * from user_table  GROUP BY name, email" )
    fun searchNoDuplicateOnNameBased(): LiveData<List<UserTable>>

    @Query("SELECT * FROM user_table WHERE name= :name AND phone= :phone")
    fun searchOnNameAndPhone(name: String, phone: String): LiveData<List<UserTable>>

    @Query("SELECT * FROM user_table WHERE name= :name AND phone IN (:phoneList)" )
    fun searchOnNameAndEmailAndPhoneList(name: String, phoneList: List<String>): LiveData<List<UserTable>>

    @Query("SELECT * FROM user_table WHERE age >= :minAge")
    fun getAllUsersOlderThan(minAge: Int): LiveData<List<UserTable>>

    @Query("SELECT * FROM user_table WHERE age BETWEEN :minAge AND :maxAge")
    fun getAllUsersBetweenAge(minAge: Int, maxAge:Int): LiveData<List<UserTable>>




}