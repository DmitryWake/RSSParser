package com.example.rssparser.ui.fragments.newslistscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.loadImage
import kotlinx.android.synthetic.main.news_item.view.*

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsListHolder>() {

    private var dataList: List<NewsModel> = listOf()
    lateinit var onItemClick: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsListHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun changeData(newData: List<NewsModel>) {
        dataList = newData
        notifyDataSetChanged()
    }

    inner class NewsListHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        init {
            view.news_item_holder_cl_container.setOnClickListener {
                onItemClick(dataList[adapterPosition].link)
            }

        }

        fun bind(newsModel: NewsModel) {
            view.news_item_holder_tv_title.text = newsModel.title
            view.news_item_holder_tv_description.text = newsModel.description
            loadImage(view.news_item_holder_iv_image, newsModel.imageUrl)
        }
    }
}