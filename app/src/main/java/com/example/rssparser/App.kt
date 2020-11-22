package com.example.rssparser

import android.app.Application
import com.example.rssparser.dagger.components.AppComponent
import com.example.rssparser.dagger.components.DaggerAppComponent
import com.example.rssparser.dagger.components.DaggerNetworkComponent
import com.example.rssparser.dagger.components.NetworkComponent
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.utilities.APP


class App : Application() {
    // Dagger App Component
    private lateinit var appComponent: AppComponent
    // Dagger Network Component
    private lateinit var networkComponent: NetworkComponent
    // Так как в репозитории содержится локальная бд
    // То обьявляем её в Application, чтобы не было
    // Повторных созданий
    lateinit var appNewsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        // Получаем ссылку на Application для удобства
        APP = this
        appComponent = DaggerAppComponent.create()
        networkComponent = DaggerNetworkComponent.create()
        appNewsRepository = appComponent.getNewsRepository()
    }

    fun appComponent() = appComponent
    fun networkComponent() = networkComponent
}