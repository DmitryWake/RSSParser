package com.example.rssparser.di.dagger.components

import com.example.rssparser.di.dagger.components.scope.ActivityScope
import com.example.rssparser.ui.fragments.detailscreen.DetailFragmentSubcomponent
import dagger.Subcomponent

/**
 * @author Ovchinnikov Roman
 */
@ActivityScope
@Subcomponent
interface MainActivitySubcomponent {
    fun detailComponent(): DetailFragmentSubcomponent
}