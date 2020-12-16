package com.example.rssparser.ui.fragments.newslistscreen.presenter

import android.annotation.SuppressLint
import com.example.rssparser.ui.fragments.newslistscreen.interactor.NewsListLoader
import com.example.rssparser.ui.fragments.newslistscreen.view.NewsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NewsListPresenter @Inject constructor(private val newsLoader: NewsListLoader) :
    MvpPresenter<NewsListView>() {

    private var loadDisposable: Disposable? = null
    private var loadFromDbDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadNewsFromDatabase()
        loadNews()
    }

    fun loadNews() {
        loadDisposable?.dispose()
        loadDisposable = newsLoader.getNewList()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.showRefreshing(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { viewState.showRefreshing(false) }
            .subscribe({ t ->
                viewState.updateView(t)
            }, { e ->
                viewState.showError(e.message.toString())
            })
    }

    @SuppressLint("CheckResult")
    private fun loadNewsFromDatabase() {
        loadFromDbDisposable?.dispose()
        newsLoader.getNewsListFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ t ->
                viewState.updateView(t)
            }, { e ->
                viewState.showError(e.message.toString())
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        loadFromDbDisposable?.dispose()
        loadDisposable?.dispose()
    }
}