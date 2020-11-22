package com.example.rssparser.room

import android.util.Log
import com.example.rssparser.room.dao.NewsDao
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.APP

class NewsRepository {
    companion object {
        const val TAG: String = "News_Repository"
    }

    private lateinit var dataBase: AppDatabase
    private lateinit var newsDao: NewsDao

    fun initDatabase() {
        dataBase = APP.appComponent().getDatabase()
        newsDao = APP.appComponent().getNewsDao()
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