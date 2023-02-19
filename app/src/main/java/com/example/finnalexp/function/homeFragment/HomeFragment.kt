package com.example.finnalexp.function.homeFragment

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finnalexp.function.FunctionActivity
import com.example.finnalexp.function.FunctionViewModel
import com.example.finnalexp.databinding.FragmentHomeBinding
import kotlinx.coroutines.*
import okhttp3.internal.wait

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Ming", "HomeFragment")
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(
            //注意这里不是this！！！
            owner = activity as FunctionActivity,
            factory = ViewModelProvider.AndroidViewModelFactory(Application())
        ).get(FunctionViewModel::class.java)

        //加载RecycleView
        val recycleView = binding.recyclerView
        //设置布局管理器
        recycleView.layoutManager = LinearLayoutManager(context)
        //设置适配器
        CoroutineScope(Dispatchers.Main).launch {
            //Room bug先传入数据
            var showData: List<StockItem> = listOf()
            showData = CoroutineScope(Dispatchers.Main).async {
                viewModel.getShowData(requireContext()) as List<StockItem>
            }.await()
            Log.d("Ming", "获取数据成功${showData.size}")
            recycleView.adapter = StockRecycleViewAdapter(showData)
            Log.d("Ming", "设置适配器成功")
        }
        //获取适配器
        val adapter = recycleView.adapter

        viewModel.userRecode.observe(viewLifecycleOwner) {
            adapter!!.notifyDataSetChanged()
        }

        return binding.root
    }

}