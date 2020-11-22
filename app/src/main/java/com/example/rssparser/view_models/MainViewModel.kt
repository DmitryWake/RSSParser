package com.example.rssparser.view_models

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rssparser.models.ArticleResponse
import com.example.rssparser.models.NewsModel
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.rss.NetworkService
import com.example.rssparser.utilities.APP_NEWS_REPOSITORY
import com.example.rssparser.utilities.formatDescription
import com.example.rssparser.views.main_screen.MainAdapter
import com.example.rssparser.views.main_screen.MainFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        var dataList: List<NewsModel> = mutableListOf()
    }

    lateinit var mAdapter: MainAdapter

    fun initViewModel() {
        mAdapter = MainAdapter(dataList)
        if (dataList.isEmpty())
            initData()
    }

    private fun initData() {
        loadFeed()
        downloadFeed()
    }

    fun saveData() {
        saveFeed()
    }

    private fun createLoadObservable(): Observable<NewsRepository> =
        Observable.create { emitter ->
            emitter.onNext(APP_NEWS_REPOSITORY)
        }

    @SuppressLint("CheckResult")
    private fun loadFeed() {
        val loadObservable = createLoadObservable()
        loadObservable
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .map { it.loadFromDatabase() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                dataList = it.sortedBy {newsModel ->
                    newsModel.pubDate
                }.reversed()
                formatData(dataList)
                mAdapter.changeData(dataList)
            }
    }

    private fun downloadFeed() {
        NetworkService
            .mInstance
            .getRSSApi()
            .getNews()
            .enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(
                    call: Call<ArticleResponse>,
                    response: Response<ArticleResponse>
                ) {
                    if (response.isSuccessful) {
                        val article = response.body()
                        val tmp = article?.channel?.newsList!!
                        if (!tmp.isNullOrEmpty()) {
                            Thread {
                                val dataToDelete = dataList
                                APP_NEWS_REPOSITORY.deleteFromDatabase(dataToDelete)
                            }.start()
                            dataList = tmp
                            formatData(dataList)
                            mAdapter.changeData(dataList)
                        }
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    Log.e(MainFragment.TAG, t.message.toString(), t)
                }
            })
    }

    private fun createSaveObservable(): Observable<List<NewsModel>> =
        Observable.create { emitter ->
            emitter.onNext(dataList)
        }

    @SuppressLint("CheckResult")
    private fun saveFeed() {
        val saveObservable = createSaveObservable()
        saveObservable.observeOn(Schedulers.io()).subscribe {
            APP_NEWS_REPOSITORY.saveToDatabase(it)
        }
    }

    private fun formatData(dataList: List<NewsModel>) {
        for (i in dataList.indices) {
            dataList[i].description = dataList[i].description.formatDescription()
        }
    }

}