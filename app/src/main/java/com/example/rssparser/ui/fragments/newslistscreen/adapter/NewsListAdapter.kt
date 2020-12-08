package com.example.rssparser.ui.fragments.newslistscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.databinding.NewsItemBinding
import com.example.rssparser.models.NewsModel
import com.example.rssparser.viewmodels.NewsItemViewModel

class NewsListAdapter() : RecyclerView.Adapter<NewsListAdapter.NewsListHolder>() {

    private var dataList: List<NewsModel> = listOf()
    lateinit var onItemClick: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListHolder {
        val binding = DataBindingUtil.inflate<NewsItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.news_item,
            parent,
            false
        )
        // Передаём биндинг для ViewModel
        return NewsListHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun changeData(newData: List<NewsModel>) {
        dataList = newData
        notifyDataSetChanged()
    }

    inner class NewsListHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var mViewModel: NewsItemViewModel

        fun bind(newsModel: NewsModel) {
            mViewModel = NewsItemViewModel()
            mViewModel.initViewModel(newsModel)
            binding.viewModel = mViewModel
            binding.newsItemContainer.setOnClickListener {
                onItemClick(newsModel.link)
            }
        }
    }
}