package com.example.rssparser.ui.fragments.detailscreen.interactor

import com.example.rssparser.database.room.repository.NewsRepositoryImpl
import com.example.rssparser.models.NewsModel
import io.reactivex.Single
import javax.inject.Inject

class NewsLoader @Inject constructor(
    private val newsRepository: NewsRepositoryImpl,
) {

    fun getNews(link: String): Single<NewsModel> {
        return Single.fromCallable { newsRepository.get(link) }
    }

}