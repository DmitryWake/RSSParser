package com.example.rssparser.ui.fragments.detailscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.rssparser.models.NewsModel
import com.example.rssparser.ui.fragments.detailscreen.interactor.NewsLoader
import com.example.rssparser.ui.fragments.newslistscreen.NewsListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val newsLoader: NewsLoader) : ViewModel(),
    LifecycleObserver {

    val linkLiveData = MutableLiveData<String>()
    val newsModelLiveData = MutableLiveData<NewsModel>()

    private var newsLoadDisposable: Disposable? = null

    @SuppressLint("CheckResult")
    fun initViewModel(link: String) {
        newsLoadDisposable?.dispose()
        newsLoadDisposable = newsLoader.getNews(link).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ t ->
                newsModelLiveData.value = t
            }, { e ->
                Log.e(NewsListViewModel.TAG, e.message.toString())
            })
    }

    // Переход в браузер по ссылке
    fun onClickReadNextButton() {
        linkLiveData.value = newsModelLiveData.value?.link
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        newsLoadDisposable?.dispose()
    }
}