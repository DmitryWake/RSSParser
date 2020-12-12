package com.example.rssparser.ui.fragments.newslistscreen.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.rssparser.models.NewsModel

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsListView : MvpView {
    fun showError(message: String)

    fun onStartLoading()

    fun onFinishLoading()

    fun updateView(newsList: List<NewsModel>)
}