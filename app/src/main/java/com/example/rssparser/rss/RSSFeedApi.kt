package com.example.rssparser.rss

import com.example.rssparser.rss.models.ArticleResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RSSFeedApi {
    @GET("news")
    //fun getNews(): Call<ArticleResponse>
    fun getNews(): Single<ArticleResponse>
}