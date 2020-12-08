package com.example.rssparser.ui.fragments.detailscreen

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssparser.app.App
import com.example.rssparser.database.room.NewsRepository
import com.example.rssparser.models.NewsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor() : ViewModel() {

    val linkLiveData = MutableLiveData<String>()

    val newsModelLiveData = MutableLiveData<NewsModel>()

    @SuppressLint("CheckResult")
    fun initViewModel(link: String) {
        val loadObservable = createLoadObservable()
        loadObservable
            // Observable создан в UI потоке
            .subscribeOn(AndroidSchedulers.mainThread())
            // Выполняем в IO потоке
            .observeOn(Schedulers.io())
            // Преобразовываем данные
            .map { it.get(link) }
            // Выполняем в UI потоке
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                newsModelLiveData.value = it
            }
    }

    // Переход в браузер по ссылке
    fun onClickReadNextButton() {
        linkLiveData.value = newsModelLiveData.value?.link
    }

    private fun createLoadObservable(): Observable<NewsRepository> =
        Observable.create { emitter ->
            emitter.onNext(App.appNewsRepository)
        }
}