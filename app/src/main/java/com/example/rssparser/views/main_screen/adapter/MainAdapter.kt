package com.example.rssparser.views.main_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.databinding.NewsItemBinding
import com.example.rssparser.models.NewsModel

class MainAdapter() : RecyclerView.Adapter<MainHolder>() {

    private var dataList: List<NewsModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = DataBindingUtil.inflate<NewsItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.news_item,
            parent,
            false
        )
        // Передаём биндинг для ViewModel
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun changeData(newData: List<NewsModel>) {
        dataList = newData
        notifyDataSetChanged()
    }
}