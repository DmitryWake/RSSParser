package com.example.rssparser.views.newslistscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.rssparser.App
import com.example.rssparser.database.room.NewsRepository
import com.example.rssparser.models.NewsModel
import com.example.rssparser.views.newslistscreen.interactor.NewsListLoader
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel @Inject constructor(private val newsLoader: NewsListLoader) : ViewModel(),
    LifecycleObserver {

    val newsListLiveData = MutableLiveData<List<NewsModel>>()
    val isRefreshingLiveData = MutableLiveData<Boolean>()

    companion object {
        // Тег для вывода в Logcat
        const val TAG = "NewsListViewModel"
    }


    // Вызывается при onCreate фрагмента
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initViewModel() {
        initData()
        println(newsListLiveData.value)
    }

    private fun initData() {
        // Сначала загружаем ленту из памяти
        // Вдруг у пользователя очень медленный интернет
        // Или вообще нет интернета
        loadFeed()
        // Скачиваем ленту с сайта
        downloadFeed()
    }

    // Observable для загрузку из локальной бд
    private fun createLoadObservable(): Observable<NewsRepository> =
        Observable.create { emitter ->
            emitter.onNext(App.appNewsRepository)
        }

    @SuppressLint("CheckResult")
    private fun loadFeed() {
        val loadObservable = createLoadObservable()
        loadObservable
            // Observable создан в UI потоке
            .subscribeOn(AndroidSchedulers.mainThread())
            // Выполняем в IO потоке
            .observeOn(Schedulers.io())
            // Преобразовываем данные
            .map { it.getAll() }
            // Выполняем в UI потоке
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty() || newsListLiveData.value.isNullOrEmpty()) {
                    newsListLiveData.value = it
                }
            }
    }

    private fun downloadFeed() {
        isRefreshingLiveData.value = true
        newsLoader.getNewList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<NewsModel>>() {
                override fun onSuccess(t: List<NewsModel>) {
                    if (t.isNotEmpty())
                        newsListLiveData.value = t
                    isRefreshingLiveData.value = false
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message.toString())
                    isRefreshingLiveData.value = false
                }
            })
    }

    fun onRefresh() {
        downloadFeed()
    }

}