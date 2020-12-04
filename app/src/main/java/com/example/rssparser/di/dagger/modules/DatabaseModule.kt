package com.example.rssparser.di.dagger.modules

import android.content.Context
import androidx.room.Room
import com.example.rssparser.database.room.AppDatabase
import com.example.rssparser.database.room.NewsMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule() {

    @Provides
    @Singleton
    fun provideNewsMapper() = NewsMapper()

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "database.db").build()

    @Provides
    @Singleton
    fun providesNewsDao(database: AppDatabase) = database.newsDao()
}