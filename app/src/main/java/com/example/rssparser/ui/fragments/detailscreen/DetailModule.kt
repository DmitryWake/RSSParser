package com.example.rssparser.ui.fragments.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Provider

/**
 * @author Ovchinnikov Roman
 */
@Suppress("UNCHECKED_CAST")
@Module
class DetailModule {

    @Provides
    fun viewModelFactory(detailProvider: Provider<DetailViewModel>): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return detailProvider.get() as T
            }
        }
    }
}