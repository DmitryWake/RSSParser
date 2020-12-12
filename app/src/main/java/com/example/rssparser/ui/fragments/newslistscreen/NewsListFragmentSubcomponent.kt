package com.example.rssparser.ui.fragments.newslistscreen

import com.example.rssparser.di.dagger.components.scope.FragmentScope
import com.example.rssparser.ui.fragments.newslistscreen.presenter.NewsListPresenter
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface NewsListFragmentSubcomponent {
    fun getNewsListPresenter(): NewsListPresenter
}