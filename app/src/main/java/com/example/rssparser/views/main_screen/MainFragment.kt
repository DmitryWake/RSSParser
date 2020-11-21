package com.example.rssparser.views.main_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.models.ArticleResponse
import com.example.rssparser.room.AppDatabaseHelper
import com.example.rssparser.models.NewsModel
import com.example.rssparser.rss.NetworkService
import com.example.rssparser.utilities.APP_DATABASE_HELPER
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val TAG = "MainFragment"
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
                    Thread {
                        APP_DATABASE_HELPER.deleteFromDatabase(dataList)
                    }.start()
                    dataList = it
                    mAdapter.changeData(it)
                }
            }
    }

    @SuppressLint("CheckResult")
    private fun downloadAndDrawFeed() {
        NetworkService
            .mInstance
            .getRSSApi()
            .getNews()
            .enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(
                    call: Call<ArticleResponse>,
                    response: Response<ArticleResponse>
                ) {
                    if (response.isSuccessful) {
                        val article = response.body()
                        dataList = article?.channel?.newsList!!
                        mAdapter.changeData(dataList)
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString(), t)
                }
            })
    }
}