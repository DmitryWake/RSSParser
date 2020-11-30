package com.example.rssparser.rss

import com.example.rssparser.rss.models.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET

interface RSSFeedApi {
    @GET("news")
    fun getNews(): Call<ArticleResponse>
}