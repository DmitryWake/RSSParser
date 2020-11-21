package com.example.rssparser.view_models

import androidx.lifecycle.ViewModel
import com.example.rssparser.models.NewsModel

open class BaseNewsViewModel: ViewModel() {

    lateinit var mNewsModel: NewsModel

    fun initViewModel(newsModel: NewsModel) {
        mNewsModel = newsModel
    }
}