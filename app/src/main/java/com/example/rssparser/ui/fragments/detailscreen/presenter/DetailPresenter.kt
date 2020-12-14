package com.example.rssparser.ui.fragments.detailscreen.presenter

import com.example.rssparser.ui.fragments.detailscreen.interactor.NewsLoader
import com.example.rssparser.ui.fragments.detailscreen.view.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class DetailPresenter @Inject constructor(private val newsLoader: NewsLoader) :
    MvpPresenter<DetailView>() {

    private var newsLoadDisposable: Disposable? = null

    fun loadNewsFromDatabase(link: String) {
        newsLoadDisposable?.dispose()
        newsLoadDisposable = newsLoader.getNews(link)
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
        newsLoadDisposable?.dispose()
    }
}