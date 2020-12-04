package com.example.rssparser.views.newslistscreen

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
class NewsListModule {

    @Provides
    fun viewModelFactory(newsListProvider: Provider<NewsListViewModel>): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return newsListProvider.get() as T
            }
        }
    }
}