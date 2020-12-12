package com.example.rssparser.ui.fragments.detailscreen

import com.example.rssparser.di.dagger.components.scope.FragmentScope
import com.example.rssparser.ui.fragments.detailscreen.presenter.DetailPresenter
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface DetailFragmentSubcomponent {
    fun getDetailPresenter(): DetailPresenter
}