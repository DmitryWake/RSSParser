package com.example.rssparser.di.dagger.modules

import com.example.rssparser.database.room.repository.NewsRepositoryImpl
import com.example.rssparser.rss.NetworkService
import com.example.rssparser.rss.RSSFeedApi
import com.example.rssparser.rss.ResponseMapper
import com.example.rssparser.views.newslistscreen.interactor.NewsListLoader
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRSSFeedApi(retrofit: Retrofit): RSSFeedApi = retrofit.create(RSSFeedApi::class.java)

    @Provides
    @Singleton
    fun provideResponseMapper(): ResponseMapper = ResponseMapper()

    @Provides
    fun providesNewsListLoader(
        rssFeedApi: RSSFeedApi,
        newsRepository: NewsRepositoryImpl,
        responseMapper: ResponseMapper
    ): NewsListLoader = NewsListLoader(rssFeedApi, newsRepository, responseMapper)
}