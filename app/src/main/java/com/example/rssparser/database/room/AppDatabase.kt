package com.example.rssparser.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rssparser.database.room.dao.NewsDao
import com.example.rssparser.database.room.entities.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}