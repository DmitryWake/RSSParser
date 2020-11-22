package com.example.rssparser.dagger.components

import com.example.rssparser.dagger.modules.NetworkModule
import com.example.rssparser.rss.NetworkService
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {
    fun getNetworkService(): NetworkService
}