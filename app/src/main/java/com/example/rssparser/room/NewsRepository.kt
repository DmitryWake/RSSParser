package com.example.rssparser.room

import com.example.rssparser.App
import com.example.rssparser.models.NewsModel
import com.example.rssparser.room.dao.NewsDao

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

    fun deleteList(dataList: List<NewsModel>) {
        newsDao.delete(dataList)
    }

    fun deleteAll() {
        newsDao.deleteAll()
    }

    fun saveList(dataList: List<NewsModel>) {
        newsDao.insert(dataList)
    }

    fun getAll(): List<NewsModel> = newsDao.getAll()
}