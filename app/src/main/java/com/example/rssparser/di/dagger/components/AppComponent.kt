package com.example.rssparser.di.dagger.components

import android.content.Context
import com.example.rssparser.database.room.AppDatabase
import com.example.rssparser.database.room.NewsRepository
import com.example.rssparser.database.room.dao.NewsDao
import com.example.rssparser.di.dagger.modules.ApiModule
import com.example.rssparser.di.dagger.modules.AppModule
import com.example.rssparser.di.dagger.modules.DatabaseModule
import com.example.rssparser.di.dagger.modules.RepositoryModule
import com.example.rssparser.rss.RSSFeedApi
import com.example.rssparser.views.newslistscreen.interactor.NewsListLoader
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
    fun getNewsRepository(): NewsRepository
    fun getDatabase(): AppDatabase
    fun getNewsDao(): NewsDao
    fun getMainActivitySubcomponent(): MainActivitySubcomponent
    fun getContext(): Context
    fun getRSSFeedApi(): RSSFeedApi
    fun getNewsListLoader(): NewsListLoader
}