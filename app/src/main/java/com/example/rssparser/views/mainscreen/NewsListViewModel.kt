package com.example.rssparser.views.mainscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.rssparser.App
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.rss.models.ArticleResponse
import com.example.rssparser.rss.models.NewsModel
import com.example.rssparser.utilities.formatDescription
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsListViewModel @Inject constructor() : ViewModel(), LifecycleObserver {

    val newsListLiveData = MutableLiveData<List<NewsModel>>()
    val isRefreshingLiveData = MutableLiveData<Boolean>()

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
            .map { it.loadFromDatabase() }
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
        App.appComponent.getNetworkService()
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
                        // Если получанная лента не пустая и содержит новости,
                        // то удаляем прошлые записи из памяти,
                        // отображаем ленту на экране
                        if (!tmp.isNullOrEmpty()) {
                            Thread {
                                App.appNewsRepository.database().clearAllTables()
                            }.start()
                            dataList = tmp
                            formatData(dataList)
                            isDataLoaded = true

                            newsListLiveData.value = dataList
                            isRefreshingLiveData.value = false
                        }
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString(), t)
                    isRefreshingLiveData.value = false
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
            App.appNewsRepository.saveToDatabase(it)
        }
    }

    // Форматируем описание NewModel
    private fun formatData(dataList: List<NewsModel>) {
        for (i in dataList.indices) {
            dataList[i].description = dataList[i].description.formatDescription()
        }
    }

    fun onRefresh() {
        downloadFeed()
    }

}