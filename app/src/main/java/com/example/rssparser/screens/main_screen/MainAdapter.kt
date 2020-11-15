package com.example.rssparser.screens.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.room.models.NewsModel

class MainAdapter(private var dataList: List<NewsModel>) :
    RecyclerView.Adapter<MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.drawItem(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun changeData(newData: List<NewsModel>) {
        dataList = newData
        notifyDataSetChanged()
    }
}