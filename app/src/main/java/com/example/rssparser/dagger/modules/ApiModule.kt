package com.example.rssparser.dagger.modules

import com.example.rssparser.rss.NetworkService
import com.example.rssparser.rss.RSSFeedApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService = NetworkService.mInstance

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(NetworkService.BASE_URL)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRSSFeedApi(retrofit: Retrofit): RSSFeedApi = retrofit.create(RSSFeedApi::class.java)
}