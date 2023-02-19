package com.example.finnalexp.loginDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "password", typeAffinity = ColumnInfo.TEXT) val password: String,
    @ColumnInfo(name = "profile") val profile: String,
    @ColumnInfo(name = "data") val data: String,
    @ColumnInfo(name = "img") val img: String,
) {

}