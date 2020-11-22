package com.example.rssparser.dagger.modules

import com.example.rssparser.rss.NetworkService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService = NetworkService.mInstance
}