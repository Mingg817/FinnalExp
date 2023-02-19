package com.example.finnalexp.Login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finnalexp.loginDatabase.UserDataBase
import com.example.finnalexp.loginDatabase.UserDataBaseAdapter
import com.example.finnalexp.databinding.ActivityLoginBinding
import com.example.finnalexp.function.FunctionActivity
import com.example.finnalexp.loginDatabase.LoginState
import com.example.finnalexp.loginDatabase.UserDao


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var database: UserDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            loginButton()
        }

        binding.regButton.setOnClickListener {
            regButton()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString("email", binding.email.text.toString())
        outState.putString("password", binding.password.text.toString())
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.email.setText(savedInstanceState.getString("email"))
        binding.password.setText(savedInstanceState.getString("password"))
    }


    override fun onDestroy() {
        super.onDestroy()
        UserDataBaseAdapter.getInstance(applicationContext).close()
    }

    private fun regButton() {
        database = UserDataBaseAdapter.getInstance(context = applicationContext)
        val dbDao = database.userDao()
        if (!checkEmpty() || !checkLength()) return@regButton
        val loginState = UserDataBaseAdapter
            .checkUser(dbDao, binding.email.text.toString(), binding.password.text.toString())
        Log.d("LoginActivity", loginState.toString())
        if (loginState == LoginState.USER_NOT_FOUND) {
            reg(dbDao)
        } else {
            Toast.makeText(baseContext, "注册失败，用户名已存在", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reg(dbDao: UserDao) {
        val result = UserDataBaseAdapter.addUser(
            dbDao,
            binding.email.text.toString(),
            binding.password.text.toString()
        )
        if (result) {
            Toast.makeText(baseContext, "注册成功", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginButton() {
        database = UserDataBaseAdapter.getInstance(context = applicationContext)
        val dbDao = database.userDao()
        if (!checkEmpty() || !checkLength()) return
        val loginState = UserDataBaseAdapter
            .checkUser(dbDao, binding.email.text.toString(), binding.password.text.toString())
        Log.d("LoginActivity", loginState.toString())
        if (loginState == LoginState.SUCCESS) {
            login()
        }
        displayToast(loginState)
    }

    private fun login() {
        Intent(this, FunctionActivity::class.java).apply {
            putExtra("id",binding.email.text.toString())
            startActivity(this)
        }
    }

    //检查是否为空
    private fun checkEmpty(): Boolean {
        if (binding.email.text.toString() == "" || binding.password.text.toString() == "") {
            displayToast(LoginState.EMPTY)
            return false
        }
        return true
    }

    //检查用户名和密码长度不得小于6位
    private fun checkLength(): Boolean {
        if (binding.email.text.toString().length < 6 || binding.password.text.toString().length < 6) {
            displayToast(LoginState.TOO_SHORT)
            return false
        }
        return true
    }


    private fun displayToast(loginState: LoginState) {
        when (loginState) {
            LoginState.USER_ALREADY_EXISTS -> {
                Toast.makeText(baseContext, "登录已经存在", Toast.LENGTH_SHORT).show()
            }
            LoginState.USER_NOT_FOUND -> {
                Toast.makeText(baseContext, "用户不存在，请先点击注册", Toast.LENGTH_SHORT).show()
            }
            LoginState.WRONG_PASSWORD -> {
                Toast.makeText(baseContext, "密码错误", Toast.LENGTH_SHORT).show()
            }
            LoginState.TOO_SHORT -> {
                Toast.makeText(baseContext, "用户名或密码长度不得小于6位", Toast.LENGTH_SHORT).show()
            }
            LoginState.EMPTY -> {
                Toast.makeText(baseContext, "用户名或密码不能为空", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(baseContext, "登录成功", Toast.LENGTH_SHORT).show()
            }
        }
    }

}