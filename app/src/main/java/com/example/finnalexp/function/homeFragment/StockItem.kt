package com.example.finnalexp.function.homeFragment

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

data class StockItem(val name: String, val code: String, val price: String, val status: Int) {
}