package com.example.rssparser.dagger.components

import com.example.rssparser.dagger.modules.AppModule
import com.example.rssparser.room.AppDatabase
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.room.dao.NewsDao
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun getNewsRepository(): NewsRepository
    fun getDatabase(): AppDatabase
    fun getNewsDao(): NewsDao
}