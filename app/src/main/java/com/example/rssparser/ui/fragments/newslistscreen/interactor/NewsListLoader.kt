package com.example.rssparser.ui.fragments.newslistscreen.interactor

import com.example.rssparser.database.room.repository.NewsRepositoryImpl
import com.example.rssparser.models.NewsModel
import com.example.rssparser.rss.RSSFeedApi
import com.example.rssparser.rss.ResponseMapper
import com.example.rssparser.rss.models.NewsModelApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Ovchinnikov Roman
 */
class NewsListLoader @Inject constructor(
    private val rSSFeedApi: RSSFeedApi,
    private val newsRepository: NewsRepositoryImpl,
    private val responseMapper: ResponseMapper
) {

    fun getNewList(): Single<List<NewsModel>> {
        return rSSFeedApi.getNews()
            .map { response -> mapToResult(response.channel.newsList) }
            .doOnSuccess { t -> saveNewsToDb(t) }
            .flatMap { Single.fromCallable { loadNewsFromDb() } }
    }

    private fun loadNewsFromDb(): List<NewsModel> {
        return newsRepository.getAll()
    }

    private fun saveNewsToDb(list: List<NewsModel>?) {
        if (!list.isNullOrEmpty()) {
            newsRepository.deleteAll()
            newsRepository.saveList(list)
        }
    }

    private fun mapToResult(newsModelApi: List<NewsModelApi>): List<NewsModel> {
        return responseMapper.toNewsModelList(newsModelApi)
    }

}