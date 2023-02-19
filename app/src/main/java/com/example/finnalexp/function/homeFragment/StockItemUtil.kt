package com.example.finnalexp.function.homeFragment

import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request

object StockItemUtil {
    fun requestInfoFromServer(id: String): JsonObject? {
        //okhttp get请求json
        val okHttpClient = OkHttpClient.Builder().build()
        //get请求
        val ip_port: String = "150.158.99.210:2345"
        val request = Request.Builder()
            .url("http://${ip_port}/request?code=${id}")
            .get()
            .build()
        val response = okHttpClient.newCall(request).execute()
        Log.d("Ming", "requestInfoFromServer${response.body?.string()}")
        val result = response.body?.string()
        return JsonParser.parseString(result).asJsonObject
    }

}