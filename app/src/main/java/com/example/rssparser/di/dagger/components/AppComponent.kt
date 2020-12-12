package com.example.rssparser.di.dagger.components

import com.example.rssparser.database.room.DatabaseModule
import com.example.rssparser.database.room.RepositoryModule
import com.example.rssparser.di.dagger.modules.ApiModule
import com.example.rssparser.di.dagger.modules.AppModule
import com.example.rssparser.ui.fragments.newslistscreen.presenters.NewsListPresenter
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [DatabaseModule::class,
        ApiModule::class,
        AppModule::class,
        RepositoryModule::class]
)
@Singleton
interface AppComponent {
    fun getNewsListPresenter(): NewsListPresenter
    fun getMainActivitySubcomponent(): MainActivitySubcomponent
}