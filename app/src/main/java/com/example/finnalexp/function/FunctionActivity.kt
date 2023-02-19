package com.example.finnalexp.function

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.finnalexp.R
import com.example.finnalexp.databinding.ActivityFunctionBinding
import com.example.finnalexp.infoDatabase.Recode
import com.example.finnalexp.infoDatabase.RecodeDataBaseAdapter

class FunctionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFunctionBinding
    lateinit var viewModel: FunctionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFunctionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment_activity_function
        )
        NavigationUI.setupWithNavController(binding.navView, navController)


        //构建ViewModel
        viewModel = ViewModelProvider(
            owner = this,
            factory = ViewModelProvider.AndroidViewModelFactory(Application())
        ).get(FunctionViewModel::class.java)

        viewModel.userId = intent.getStringExtra("id")!!

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_fragment_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_menu -> {
                val dao = RecodeDataBaseAdapter.getInstance(applicationContext).userInFoDao()
                dao.insertUserCode(Recode(viewModel.userId, "sh600475"))
                dao.getUserCodeList(viewModel.userId).value?.forEach {
                    Log.d("Ming", it)
                }
            }
            R.id.help_menu -> {
                Log.d("Ming", "Help")
                AlertDialog.Builder(this)
                    .setTitle("帮助")
                    .setMessage("""
                        帮助文件：\n
                        利用MA平均线判断股票趋势，MA7>MA30>MA180 推荐买入，MA7<MA30<MA180 推荐卖出
                    
                    """.trimIndent())
                    .show()
            }
        }
        return true
    }

}