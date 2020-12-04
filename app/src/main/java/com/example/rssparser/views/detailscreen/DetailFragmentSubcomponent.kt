package com.example.rssparser.views.detailscreen

import androidx.lifecycle.ViewModelProvider
import com.example.rssparser.di.dagger.components.scope.FragmentScope
import dagger.Subcomponent

/**
 * @author Ovchinnikov Roman
 */
@FragmentScope
@Subcomponent(modules = [DetailModule::class])
interface DetailFragmentSubcomponent {
    fun viewModelFactory(): ViewModelProvider.Factory
}