package com.example.finnalexp.loginDatabase

enum class LoginState {
    USER_NOT_FOUND,
    USER_ALREADY_EXISTS,
    WRONG_PASSWORD,
    TOO_SHORT,
    EMPTY,
    SUCCESS
}