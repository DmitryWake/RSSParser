package com.example.rssparser.ui.fragments.detailscreen.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.rssparser.models.NewsModel

@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailView : MvpView {
    fun updateView(newsModel: NewsModel)

    fun showError(message: String)
}