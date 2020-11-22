package com.example.rssparser.dagger.modules

import androidx.room.Room
import com.example.rssparser.room.AppDatabase
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.utilities.APP_ACTIVITY
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(): NewsRepository = NewsRepository()

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase =
        Room.databaseBuilder(APP_ACTIVITY, AppDatabase::class.java, "database").build()

    @Provides
    @Singleton
    fun providesNewsDao(database: AppDatabase) = database.newsDao()
}