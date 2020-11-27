package com.example.rssparser.dagger.modules

import android.content.Context
import androidx.room.Room
import com.example.rssparser.room.AppDatabase
import com.example.rssparser.room.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule() {

    @Provides
    @Singleton
    fun provideRepository(): NewsRepository = NewsRepository()

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "database.db").build()

    @Provides
    @Singleton
    fun providesNewsDao(database: AppDatabase) = database.newsDao()
}