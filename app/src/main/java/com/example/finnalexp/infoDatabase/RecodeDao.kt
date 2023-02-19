package com.example.finnalexp.infoDatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserCode(vararg recode: Recode){
        Log.d("Ming","insertUserCode")
    }

    @Delete
    fun deleteUserCode(vararg recode: Recode)

    @Update
    fun updateUserCode(vararg recode: Recode)

    @Query("SELECT code FROM recodes WHERE id = :id")
    fun getUserCodeList(id: String): LiveData<List<String>>

    @Query("SELECT * FROM recodes WHERE id='123456' ")
    fun getUserCodeList2(): Recode


}