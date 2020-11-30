package com.example.rssparser.views.mainscreen.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.databinding.NewsItemBinding
import com.example.rssparser.rss.models.NewsModel
import com.example.rssparser.viewmodels.NewsItemViewModel

class MainHolder(private val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root) {

    lateinit var mViewModel: NewsItemViewModel

    fun bind(newsModel: NewsModel) {
        mViewModel = NewsItemViewModel()
        mViewModel.initViewModel(newsModel)
        binding.viewModel = mViewModel
    }
}