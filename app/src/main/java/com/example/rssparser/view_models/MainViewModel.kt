package com.example.rssparser.view_models

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.example.rssparser.models.ArticleResponse
import com.example.rssparser.models.NewsModel
import com.example.rssparser.room.NewsRepository
import com.example.rssparser.utilities.APP
import com.example.rssparser.utilities.formatDescription
import com.example.rssparser.views.main_screen.MainAdapter
import com.example.rssparser.views.main_screen.MainFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(), LifecycleObserver {

    companion object {
        // Можно было реализовать через LiveData
        // Но я пока плохо с этим знаком
        var dataList: List<NewsModel> = mutableListOf()
        var isDataLoaded = false

        // Тег для вывода в Logcat
        const val TAG = "MainFragment"
    }

    lateinit var mAdapter: MainAdapter

    // Вызывается при onCreate фрагмента
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initViewModel() {
        mAdapter = MainAdapter(dataList)
        if (dataList.isEmpty())
            initData()
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
            emitter.onNext(APP.appNewsRepository)
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
                dataList = it
                mAdapter.changeData(dataList)
            }
    }

    private fun downloadFeed() {
        APP.networkComponent().getNetworkService()
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
                                APP.appNewsRepository.database().clearAllTables()
                            }.start()
                            dataList = tmp
                            formatData(dataList)
                            isDataLoaded = true
                            mAdapter.changeData(dataList)
                        }
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString(), t)
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
            APP.appNewsRepository.saveToDatabase(it)
        }
    }

    // Форматируем описание NewModel
    private fun formatData(dataList: List<NewsModel>) {
        for (i in dataList.indices) {
            dataList[i].description = dataList[i].description.formatDescription()
        }
    }

}