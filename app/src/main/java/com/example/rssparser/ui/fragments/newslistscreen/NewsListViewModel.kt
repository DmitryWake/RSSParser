package com.example.rssparser.ui.fragments.newslistscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.rssparser.models.NewsModel
import com.example.rssparser.ui.fragments.newslistscreen.interactor.NewsListLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel @Inject constructor(private val newsLoader: NewsListLoader) : ViewModel(),
    LifecycleObserver {

    val newsListLiveData = MutableLiveData<List<NewsModel>>()
    val isEmptyLiveData = MutableLiveData<Boolean>()
    val isRefreshingLiveData = MutableLiveData<Boolean>()

    private var loadDisposable: Disposable? = null
    private var loadFromDbDisposable: Disposable? = null

    companion object {
        // Тег для вывода в Logcat
        const val TAG = "NewsListViewModel"
    }


    // Вызывается при onCreate фрагмента
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initViewModel() {
        initData()
    }

    private fun initData() {
        // Сначала загружаем ленту из памяти
        // Вдруг у пользователя очень медленный интернет
        // Или вообще нет интернета
        loadNewsFromDb()
        // Скачиваем ленту с сайта
        loadNews()
    }

    @SuppressLint("CheckResult")
    private fun loadNewsFromDb() {
        loadFromDbDisposable?.dispose()
        loadFromDbDisposable = newsLoader.getNewsListFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ t ->
                if (t.isNotEmpty()) {
                    newsListLiveData.value = t
                }
                isEmptyLiveData.value = t.isEmpty()
            }, { e ->
                Log.e(TAG, e.message.toString())
                isEmptyLiveData.value = true
            })
    }

    private fun loadNews() {
        loadDisposable?.dispose()
        loadDisposable = newsLoader.getNewList()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { isRefreshingLiveData.value = true }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { isRefreshingLiveData.value = false }
            .subscribe({ t ->
                if (t.isNotEmpty()) {
                    newsListLiveData.value = t
                }
                isEmptyLiveData.value = t.isEmpty()
            }, { e ->
                Log.e(TAG, e.message.toString())
                isEmptyLiveData.value = true
            })
    }

    fun onRefresh() {
        loadNews()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        loadDisposable?.dispose()
        loadFromDbDisposable?.dispose()
    }

}