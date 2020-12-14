package com.example.rssparser.ui.activities

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface MainView : MvpView {
    fun showNewsListFragment()
}