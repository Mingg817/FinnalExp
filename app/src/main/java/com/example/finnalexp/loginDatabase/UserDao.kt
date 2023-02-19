package com.example.finnalexp.loginDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(vararg user: User)

    @Delete
    fun deleteUser(vararg user: User)

    @Update
    fun updateUser(vararg user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: String): User

    @Query("SELECT password FROM user WHERE id = :id")
    fun getUserPassword(id: String): String?

    //获得这个用户的信息类
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserLiveData(id: String): LiveData<User>

}