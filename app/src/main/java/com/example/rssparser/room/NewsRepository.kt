package com.example.rssparser.room

import android.util.Log
import com.example.rssparser.App
import com.example.rssparser.room.dao.NewsDao
import com.example.rssparser.rss.models.NewsModel

class NewsRepository {
    companion object {
        // Тег для консоли
        const val TAG: String = "News_Repository"
    }

    private lateinit var dataBase: AppDatabase
    private lateinit var newsDao: NewsDao

    // Получение с помощью Dagger
    fun initDatabase() {
        dataBase = App.appComponent.getDatabase()
        newsDao = App.appComponent.getNewsDao()
    }

    fun deleteFromDatabase(dataList: List<NewsModel>) {
        newsDao.delete(dataList)
    }

    fun saveToDatabase(dataList: List<NewsModel>) {
        newsDao.insert(dataList)
    }

    fun loadFromDatabase(): List<NewsModel> {
        return try {
            newsDao.getAll()
        } catch (t: Throwable) {
            Log.e(TAG, "Loading error: ${t.message.toString()}")
            listOf()
        }
    }

    fun database() = dataBase
}