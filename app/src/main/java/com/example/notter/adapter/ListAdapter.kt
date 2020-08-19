package com.example.notter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notter.data.models.NotterData
import com.example.notter.databinding.RowPageLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<NotterData>()

    class MyViewHolder(private val binding: RowPageLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(notterData: NotterData){
            binding.notterData = notterData
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowPageLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(
                    binding
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(
            parent
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = dataList[position]
        holder.binding(currentItem)
    }

    fun setData(notterData: List<NotterData>){
        val notterDiffUtil = NotterDiffUtil(dataList, notterData)
        val notterDiffResult = DiffUtil.calculateDiff(notterDiffUtil)
        this.dataList = notterData
        notterDiffResult.dispatchUpdatesTo(this)
    }
}