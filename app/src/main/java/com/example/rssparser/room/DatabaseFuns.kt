package com.example.rssparser.room

import android.util.Log
import androidx.room.Room
import com.example.rssparser.room.dao.NewsDao
import com.example.rssparser.room.models.NewsModel
import com.example.rssparser.utilities.APP_ACTIVITY
import java.io.IOException

const val TAG: String = "Database"

lateinit var dataBase: AppDatabase
lateinit var newsDao: NewsDao


fun initDatabase() {
    dataBase =
        Room.databaseBuilder(APP_ACTIVITY, AppDatabase::class.java, "database").build()
    newsDao = dataBase.newsDao()
}

fun saveToDatabase(dataList: List<NewsModel>) {
    val task = Runnable {
        newsDao.insert(dataList)
    }
    Thread(task).start()
}

fun loadFromDatabase(function: (List<NewsModel>) -> Unit) {
    try {
        val task = Runnable {
            function(newsDao.getAll())
        }
        Thread(task).start()
    } catch (e: IOException) {
        Log.e(TAG, "Loading error: ${e.message.toString()}")
    }
}