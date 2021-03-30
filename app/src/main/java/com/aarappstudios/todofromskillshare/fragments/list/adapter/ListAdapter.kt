package com.aarappstudios.todofromskillshare.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aarappstudios.todofromskillshare.data.models.TodoData
import com.aarappstudios.todofromskillshare.databinding.RowLayoutBinding
import com.aarappstudios.todofromskillshare.fragments.list.TodoDiffUtils

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
     public var dataList = emptyList<TodoData>()

    class MyViewHolder(private val binding:RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoData: TodoData)
        {
            binding.tododata=todoData
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=RowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = dataList[position]
        holder.bind(todoData)
    }

    fun addData(list: List<TodoData>) {
        val todoDiffutil=TodoDiffUtils(dataList,list)
        val tododiffresult=DiffUtil.calculateDiff(todoDiffutil)
        this.dataList = list
        tododiffresult.dispatchUpdatesTo(this)
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

}