package com.example.rssparser.rss

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NetworkService private constructor() {
    companion object {
        val mInstance: NetworkService = NetworkService()
        const val BASE_URL = "https://lenta.ru/rss/"
    }

    private var mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    fun getRSSApi(): RSSFeedApi = mRetrofit.create(RSSFeedApi::class.java)
}