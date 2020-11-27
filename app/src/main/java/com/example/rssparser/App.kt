package com.example.rssparser

import android.app.Application
import com.example.rssparser.dagger.components.AppComponent
import com.example.rssparser.dagger.components.DaggerAppComponent
import com.example.rssparser.dagger.modules.AppModule
import com.example.rssparser.room.NewsRepository


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
        appNewsRepository = appComponent.getNewsRepository()
        appNewsRepository.initDatabase()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        // Dagger App Component
        lateinit var appComponent: AppComponent
        lateinit var appNewsRepository: NewsRepository
    }
}