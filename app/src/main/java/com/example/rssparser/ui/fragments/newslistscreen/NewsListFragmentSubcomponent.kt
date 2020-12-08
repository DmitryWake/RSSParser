package com.example.rssparser.ui.fragments.newslistscreen

import androidx.lifecycle.ViewModelProvider
import com.example.rssparser.di.dagger.components.scope.FragmentScope
import dagger.Subcomponent

/**
 * @author Ovchinnikov Roman
 */
@FragmentScope
@Subcomponent(modules = [NewsListModule::class])
interface NewsListFragmentSubcomponent {
    fun viewModelFactory(): ViewModelProvider.Factory
}