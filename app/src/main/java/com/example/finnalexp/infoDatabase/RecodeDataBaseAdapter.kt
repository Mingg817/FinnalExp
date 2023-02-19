package com.example.finnalexp.infoDatabase

import android.content.Context
import androidx.room.Room

object RecodeDataBaseAdapter {
    private var instance: RecodeDataBase? = null
    fun getInstance(context: Context): RecodeDataBase {
        if (instance == null) {
            synchronized(RecodeDataBase::class) {
                instance = Room.databaseBuilder(
                    context,
                    RecodeDataBase::class.java,
                    "user_info.db"
                ).allowMainThreadQueries().build()
            }
        }
        return instance!!
    }
}