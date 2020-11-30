package com.example.rssparser.rss

import com.example.rssparser.App

class NetworkService private constructor() {
    companion object {
        // Получение постоянного состояния
        val mInstance: NetworkService = NetworkService()
        // Ссылка на ленту
        const val BASE_URL = "https://lenta.ru/rss/"
    }

    fun getRSSApi(): RSSFeedApi = App.appComponent.getRSSFeedApi()
}