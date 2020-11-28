package com.example.rssparser.dagger.components

import com.example.rssparser.dagger.components.scope.ActivityScope
import com.example.rssparser.views.detailscreen.DetailFragmentSubcomponent
import com.example.rssparser.views.mainscreen.NewsListFragmentSubcomponent
import dagger.Subcomponent

/**
 * @author Ovchinnikov Roman
 */
@ActivityScope
@Subcomponent
interface MainActivitySubcomponent {
    fun newsListComponent(): NewsListFragmentSubcomponent
    fun detailComponent(): DetailFragmentSubcomponent
}