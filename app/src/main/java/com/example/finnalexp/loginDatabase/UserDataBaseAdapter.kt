package com.example.finnalexp.loginDatabase

import android.content.Context
import android.security.identity.AccessControlProfile
import androidx.room.Room
import java.security.MessageDigest

object UserDataBaseAdapter {
    private var instance: UserDataBase? = null
    fun getInstance(context: Context): UserDataBase {
        if (instance == null) {
            synchronized(UserDataBase::class) {
                instance = Room.databaseBuilder(
                    context,
                    UserDataBase::class.java,
                    "user.db"
                ).allowMainThreadQueries().build()
            }
        }
        return instance!!
    }

    fun checkUser(dbDao: UserDao, id: String, password: String): LoginState {
        val user = dbDao.getUser(id) ?: return LoginState.USER_NOT_FOUND
        if (user.password != md5(password)) {
            return LoginState.WRONG_PASSWORD
        }
        return LoginState.SUCCESS
    }

    fun addUser(dbDao: UserDao, id: String, password: String): Boolean {
        dbDao.insertUser(User(id, md5(password), "用户介绍", "24", "https://img.zcool.cn/community/016a2e5f110b9fa801215aa097202c.png"))
        return true
    }

    fun updateUser(
        dbDao: UserDao,
        id: String,
        profile: String,
        data: String,
        img: String
    ): Boolean {
        dbDao.updateUser(User(id, dbDao.getUserPassword(id)!!, profile, data, img))
        return true
    }

    //md5加密
    private fun md5(str: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        val bytes = md5.digest(str.toByteArray())
        return bytesToHex(bytes)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            val hex = Integer.toHexString(0xff and b.toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }
}