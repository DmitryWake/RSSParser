package com.example.rssparser.dagger.components

import com.example.rssparser.dagger.components.scope.ActivityScope
import dagger.Subcomponent

/**
 * @author Ovchinnikov Roman
 */
@ActivityScope
@Subcomponent
interface MainActivitySubcomponent {
    fun revListComponent(): NewsListFragmentSubcomponent
}