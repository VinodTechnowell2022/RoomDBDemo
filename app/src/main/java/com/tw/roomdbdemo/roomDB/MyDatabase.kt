package com.tw.roomdbdemo.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tw.roomdbdemo.daos.UserDao
import com.tw.roomdbdemo.models.UserTable

@Database(entities =
[
    UserTable::class
], version = 1, exportSchema = false)

abstract class MyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "my_app_db")
                .build()
    }

}