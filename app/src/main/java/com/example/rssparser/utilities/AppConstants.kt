package com.example.rssparser.utilities

import com.example.rssparser.MainActivity
import com.example.rssparser.room.NewsRepository

lateinit var APP_ACTIVITY: MainActivity

lateinit var APP_NEWS_REPOSITORY: NewsRepository

const val FRAGMENT_KEY = "fragment"

const val URL_LENTA_RU = "https://lenta.ru/rss/news"