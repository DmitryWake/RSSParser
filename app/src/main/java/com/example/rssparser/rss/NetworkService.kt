package com.example.rssparser.rss

import com.example.rssparser.App
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NetworkService private constructor() {
    companion object {
        // Получение постоянного состояния
        val mInstance: NetworkService = NetworkService()
        // Ссылка на ленту
        const val BASE_URL = "https://lenta.ru/rss/"
    }

    private val mRetrofit: Retrofit = App.appComponent.getRetrofit()

    fun getRSSApi(): RSSFeedApi = App.appComponent.getRSSFeedApi()
}