package com.example.rssparser.app

import android.app.Application
import com.example.rssparser.di.dagger.components.AppComponent
import com.example.rssparser.di.dagger.components.DaggerAppComponent
import com.example.rssparser.di.dagger.modules.AppModule


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        // Dagger App Component
        lateinit var appComponent: AppComponent
    }
}