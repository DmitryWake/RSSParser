package com.example.rssparser.database.room

import com.example.rssparser.models.NewsModel

interface NewsRepository {
    fun deleteAll()
    fun saveList(dataList: List<NewsModel>)
    fun getAll(): List<NewsModel>
    fun get(link: String): NewsModel
}