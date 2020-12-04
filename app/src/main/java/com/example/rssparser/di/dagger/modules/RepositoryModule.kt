package com.example.rssparser.di.dagger.modules

import com.example.rssparser.database.room.NewsRepository
import com.example.rssparser.database.room.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun newsRepository(newsRepository: NewsRepositoryImpl): NewsRepository
}