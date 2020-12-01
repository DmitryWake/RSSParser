package com.example.rssparser.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rssparser.models.NewsModel
import com.example.rssparser.room.dao.NewsDao

@Database(entities = [NewsModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}