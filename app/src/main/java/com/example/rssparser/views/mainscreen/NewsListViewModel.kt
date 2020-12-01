package com.example.rssparser.views.mainscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.rssparser.App
import com.example.rssparser.models.NewsModel
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.rss.models.NewsModelApi
import com.example.rssparser.utilities.formatDescription
import com.example.rssparser.views.mainscreen.interactor.NewsListLoader
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel @Inject constructor(private val newsLoader: NewsListLoader) : ViewModel(),
    LifecycleObserver {

    val newsListLiveData = MutableLiveData<List<NewsModel>>()
    val isRefreshingLiveData = MutableLiveData<Boolean>()
    lateinit var loader: NewsListLoader

    companion object {
        // Можно было реализовать через LiveData
        // Но я пока плохо с этим знаком
        var dataList: List<NewsModel> = mutableListOf()
        var isDataLoaded = false

        // Тег для вывода в Logcat
        const val TAG = "MainFragment"
    }


    // Вызывается при onCreate фрагмента
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initViewModel() {
        loader = App.appComponent.getNewsListLoader()
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun saveData() {
        // если мы обновляли список - то сохраняем
        if (isDataLoaded)
            saveFeed()
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
        loader.getNewList()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableSingleObserver<List<NewsModel>>() {
                override fun onSuccess(t: List<NewsModel>) {
                    if (t.isNotEmpty()) {
                        newsListLiveData.value = t
                    }
                    isRefreshingLiveData.value = false
                }

                override fun onError(e: Throwable) {
                    isRefreshingLiveData.value = false
                    Log.e(TAG, e.message.toString())
                }

            })
    }

    private fun createSaveObservable(): Observable<List<NewsModel>> =
        Observable.create { emitter ->
            // Помещаем лист на сохранение в эмитер
            emitter.onNext(dataList)
        }

    @SuppressLint("CheckResult")
    private fun saveFeed() {
        val saveObservable = createSaveObservable()
        // Сохраняем в IO потоке
        saveObservable.observeOn(Schedulers.io()).subscribe {
            App.appNewsRepository.saveList(it)
        }
    }

    // Форматируем описание NewModel
    private fun formatData(dataList: List<NewsModelApi>) {
        for (i in dataList.indices) {
            dataList[i].description = dataList[i].description.formatDescription()
        }
    }

    fun onRefresh() {
        downloadFeed()
    }

}