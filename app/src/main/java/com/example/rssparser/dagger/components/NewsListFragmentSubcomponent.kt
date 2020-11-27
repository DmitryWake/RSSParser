package com.example.rssparser.dagger.components

import androidx.lifecycle.ViewModelProvider
import com.example.rssparser.dagger.components.scope.FragmentScope
import dagger.Subcomponent

/**
 * @author Ovchinnikov Roman
 */
@FragmentScope
@Subcomponent(modules = [NewsListModule::class])
interface NewsListFragmentSubcomponent {
    fun viewModelFactory(): ViewModelProvider.Factory
}