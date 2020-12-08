package com.example.rssparser.database.room

import android.content.Context
import androidx.room.Room
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