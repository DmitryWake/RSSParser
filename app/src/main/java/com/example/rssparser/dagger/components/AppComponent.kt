package com.example.rssparser.dagger.components

import android.content.Context
import com.example.rssparser.dagger.modules.ApiModule
import com.example.rssparser.dagger.modules.AppModule
import com.example.rssparser.dagger.modules.DatabaseModule
import com.example.rssparser.room.AppDatabase
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.room.dao.NewsDao
import com.example.rssparser.rss.RSSFeedApi
import com.example.rssparser.views.mainscreen.interactor.NewsListLoader
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DatabaseModule::class, ApiModule::class, AppModule::class])
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