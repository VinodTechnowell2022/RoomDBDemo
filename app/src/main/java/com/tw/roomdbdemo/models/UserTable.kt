package com.tw.roomdbdemo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class UserTable(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var name: String,

    var email: String,

    var phone: String

): Parcelable {
    override fun toString(): String {
        return "\nToDoData(id=$id, name='$name', email=$email, phone='$phone')"
    }
}
