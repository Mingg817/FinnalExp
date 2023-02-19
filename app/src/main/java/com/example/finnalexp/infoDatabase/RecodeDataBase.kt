package com.example.finnalexp.infoDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recode::class], version = 1, exportSchema = false)
abstract class RecodeDataBase : RoomDatabase() {
    abstract fun userInFoDao(): RecodeDao
}