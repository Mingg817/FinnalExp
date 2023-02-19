package com.example.finnalexp.function

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finnalexp.function.homeFragment.StockItem
import com.example.finnalexp.function.homeFragment.StockItemUtil
import com.example.finnalexp.infoDatabase.Recode
import com.example.finnalexp.infoDatabase.RecodeDataBaseAdapter
import com.example.finnalexp.loginDatabase.User
import com.example.finnalexp.loginDatabase.UserDataBaseAdapter
import kotlinx.coroutines.*

class FunctionViewModel : ViewModel() {
    //用户ID
    var userId: String = "123456"

    //用户，使用LiveData
    private var _userRecode = MutableLiveData<Recode>()
    val userRecode: LiveData<Recode> = _userRecode
    fun getUserRecode(context: Context): LiveData<Recode> {
        if (_userRecode.value == null) {
            val dao = RecodeDataBaseAdapter.getInstance(context = context.applicationContext)
                .userInFoDao()
            _userRecode = dao.getUserCodeList(userId) as MutableLiveData<Recode>
        }
        return userRecode
    }


    //显示信息
    suspend fun getShowData(context: Context): List<StockItem> {
        val codeList = MutableLiveData<MutableList<String>>()
        codeList.value = mutableListOf()
        //Test Code
        codeList.value?.add("sh603719")
        codeList.value?.add("sh600475")
        return CoroutineScope(Dispatchers.IO).async {
            Log.d("Ming", "getShowData")
            Log.d("Ming", "${codeList.value?.size}")
            Log.d("Ming", "codeList.value?.size ?: 0=${codeList.value?.size ?: 0}")
            return@async MutableList<StockItem>(codeList.value?.size ?: 0) {
                delay(500)
                val jsonElement = StockItemUtil.requestInfoFromServer(codeList.value!![it])
                val now_price = jsonElement?.get("now_price")?.asString ?: "Error"
                val MA7 = jsonElement?.get("MA7")?.asInt ?: 0
                val MA30 = jsonElement?.get("MA30")?.asInt ?: 0
                val MA180 = jsonElement?.get("MA180")?.asInt ?: 0
                val name=jsonElement?.get("name")?.asString ?: "Error"
                StockItem(
                    name,
                    codeList.value!![it],
                    now_price,
                    judge(MA7, MA30, MA180),
                )
            }
        }.await()
    }

    private fun judge(ma7: Int, ma30: Int, ma180: Int): Int {
        if (ma7 > ma30 && ma30 > ma180) {
            return -1
        }
        if (ma7 < ma30 && ma30 < ma180) {
            return 1
        }
        return 0
    }


}