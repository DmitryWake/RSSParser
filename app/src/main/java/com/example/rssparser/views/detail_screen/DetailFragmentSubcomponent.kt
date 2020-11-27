package com.example.rssparser.views.detail_screen

import androidx.lifecycle.ViewModelProvider
import com.example.rssparser.dagger.components.scope.FragmentScope
import dagger.Subcomponent

/**
 * @author Ovchinnikov Roman
 */
@FragmentScope
@Subcomponent(modules = [DetailModule::class])
interface DetailFragmentSubcomponent {
    fun viewModelFactory(): ViewModelProvider.Factory
}