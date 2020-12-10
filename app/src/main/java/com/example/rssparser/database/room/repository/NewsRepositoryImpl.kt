package com.example.rssparser.database.room.repository

import com.example.rssparser.database.repository.NewsRepository
import com.example.rssparser.database.room.NewsMapper
import com.example.rssparser.database.room.dao.NewsDao
import com.example.rssparser.models.NewsModel
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsMapper: NewsMapper,
    private val newsDao: NewsDao
) : NewsRepository {

    override fun deleteAll() {
        newsDao.deleteAll()
    }

    override fun saveList(dataList: List<NewsModel>) {
        newsDao.insert(newsMapper.toNewsEntityList(dataList))
    }

    override fun getAll(): List<NewsModel> {
        return newsMapper.toNewsModelList(newsDao.getAll())
    }

    override fun get(link: String): NewsModel {
        return newsMapper.toNewsModel(newsDao.get(link))
    }
}