package com.example.rssparser

import android.app.Application
import com.example.rssparser.dagger.components.AppComponent
import com.example.rssparser.dagger.components.DaggerAppComponent
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.utilities.APP

class App : Application() {
    private lateinit var appComponent: AppComponent
    lateinit var appNewsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        APP = this
        appComponent = DaggerAppComponent.create()
        appNewsRepository = appComponent.getNewsRepository()
    }

    fun getAppComponent() = appComponent
}