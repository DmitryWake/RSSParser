package com.example.rssparser.ui.fragments.newslistscreen.view

import com.example.rssparser.models.NewsModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsListView : MvpView {
    fun showError(message: String)

    fun showRefreshing(isRefresh: Boolean)

    fun updateView(newsList: List<NewsModel>)
}