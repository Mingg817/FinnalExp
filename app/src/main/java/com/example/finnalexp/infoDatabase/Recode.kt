package com.example.finnalexp.infoDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recodes")
class Recode(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "code") val code: String
) {

}