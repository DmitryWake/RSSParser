package com.example.rssparser.ui.fragments.detailscreen.view

import com.example.rssparser.models.NewsModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailView : MvpView {
    fun updateView(newsModel: NewsModel)

    fun showError(message: String)
}