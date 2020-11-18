package com.example.rssparser.utilities

import com.example.rssparser.MainActivity
import com.example.rssparser.room.AppDatabaseHelper

lateinit var APP_ACTIVITY: MainActivity

lateinit var APP_DATABASE_HELPER: AppDatabaseHelper

const val FRAGMENT_KEY = "fragment"

const val URL_LENTA_RU = "https://lenta.ru/rss/news"