package com.example.rssparser.rss

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NetworkService private constructor() {
    companion object {
        // Получение постоянного состояния
        val mInstance: NetworkService = NetworkService()
        // Ссылка на ленту
        const val BASE_URL = "https://lenta.ru/rss/"
    }

    private val mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    fun getRSSApi(): RSSFeedApi = mRetrofit.create(RSSFeedApi::class.java)
}