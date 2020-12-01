package com.example.rssparser.rss

class NetworkService private constructor() {
    companion object {
        // Получение постоянного состояния
        val mInstance: NetworkService = NetworkService()
        // Ссылка на ленту
        const val BASE_URL = "https://lenta.ru/rss/"
    }
}