package com.example.rssparser.viewmodels

import androidx.lifecycle.ViewModel
import com.example.rssparser.models.NewsModel

open class NewsItemViewModel : ViewModel() {

    lateinit var mNewsModel: NewsModel

    fun initViewModel(newsModel: NewsModel) {
        mNewsModel = newsModel
    }
}