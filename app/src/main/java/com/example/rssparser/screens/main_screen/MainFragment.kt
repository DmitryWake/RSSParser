package com.example.rssparser.screens.main_screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.room.AppDatabaseHelper
import com.example.rssparser.room.models.NewsModel
import com.example.rssparser.rss.RSSParser
import com.example.rssparser.utilities.APP_DATABASE_HELPER
import com.example.rssparser.utilities.URL_LENTA_RU
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.fragment_main.*
import java.net.URL


class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        var mRecyclerViewPosition: Int = 0
        private var dataList: List<NewsModel> = mutableListOf()
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (dataList.isEmpty()) {
            loadAndDrawFeed()
            downloadAndDrawFeed()
        }

    }

    override fun onStart() {
        super.onStart()
        mLayoutManager = LinearLayoutManager(this.context)
        initRecyclerView()
        mRecyclerView.scrollToPosition(mRecyclerViewPosition)
    }

    override fun onStop() {
        super.onStop()
        saveFeed()
    }

    override fun onPause() {
        super.onPause()
        mRecyclerViewPosition = mLayoutManager.findFirstVisibleItemPosition()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_recycler_view

        mAdapter = MainAdapter(dataList)

        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = mLayoutManager
    }

    private fun createDownloadObservable(): Observable<RSSParser> = Observable.create { emitter ->
        emitter.onNext(RSSParser(URL(URL_LENTA_RU)))
    }

    private fun createLoadObservable(): Observable<AppDatabaseHelper> =
        Observable.create { emitter ->
            emitter.onNext(APP_DATABASE_HELPER)
        }

    private fun createSaveObservable(): Observable<List<NewsModel>> =
        Observable.create { emitter ->
            emitter.onNext(dataList)
        }

    @SuppressLint("CheckResult")
    private fun saveFeed() {
        val saveObservable = createSaveObservable()
        saveObservable.observeOn(io()).subscribe {
            APP_DATABASE_HELPER.saveToDatabase(it)
        }
    }

    @SuppressLint("CheckResult")
    private fun loadAndDrawFeed() {
        val loadObservable = createLoadObservable()
        loadObservable
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(io())
            .map { it.loadFromDatabase() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    dataList = it
                    mAdapter.changeData(it)
                }
            }
    }

    @SuppressLint("CheckResult")
    private fun downloadAndDrawFeed() {

        val downloadObservable = createDownloadObservable()
        downloadObservable
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(io())
            .map { it.readFeed() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                dataList = result
                mAdapter.changeData(dataList)
            }
    }
}