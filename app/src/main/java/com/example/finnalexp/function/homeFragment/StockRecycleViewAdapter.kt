package com.example.finnalexp.function.homeFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.finnalexp.databinding.ItemLayoutBinding

class StockRecycleViewAdapter(private val dataSet: List<StockItem>) :
    RecyclerView.Adapter<StockRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var binding: ItemLayoutBinding

        constructor(binding: ItemLayoutBinding) : this(binding.root) {
            this.binding = binding
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        Log.d("ming", "onBindViewHolder")
        val data = dataSet.get(position)
        viewHolder.binding.stockItem = data
        when (data.status) {
            1 -> viewHolder.binding.resultTextView.text = "买入"
            0 -> viewHolder.binding.resultTextView.text = "观望"
            -1 -> viewHolder.binding.resultTextView.text = "卖出"
        }
    }

    override fun getItemCount() = dataSet.size

}


