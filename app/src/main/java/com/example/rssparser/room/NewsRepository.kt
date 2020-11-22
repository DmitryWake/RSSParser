package com.example.rssparser.room

import android.util.Log
import androidx.room.Room
import com.example.rssparser.room.dao.NewsDao
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.APP_ACTIVITY

class NewsRepository {
    companion object {
        const val TAG: String = "News_Repository"
    }

    private lateinit var dataBase: AppDatabase
    private lateinit var newsDao: NewsDao

    fun initDatabase() {
        dataBase =
            Room.databaseBuilder(APP_ACTIVITY, AppDatabase::class.java, "database").build()
        newsDao = dataBase.newsDao()
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
}