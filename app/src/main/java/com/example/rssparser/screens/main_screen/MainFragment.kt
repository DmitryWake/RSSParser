package com.example.rssparser.screens.main_screen

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.room.loadFromDatabase
import com.example.rssparser.room.models.NewsModel
import com.example.rssparser.room.newsDao
import com.example.rssparser.room.saveToDatabase
import com.example.rssparser.rss.RSSParser
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
            loadFromDatabase {
                android.os.Handler(Looper.getMainLooper()).post {
                    kotlin.run {
                        dataList = it
                        mAdapter.changeData(dataList)
                    }
                    downloadData()
                }
            }
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
        saveToDatabase(dataList)
    }

    override fun onPause() {
        super.onPause()
        mRecyclerViewPosition = mLayoutManager.findFirstVisibleItemPosition()
    }


    private fun downloadData() {
        val parser = RSSParser(URL("https://lenta.ru/rss/news"))

        val task = Runnable {
            val tmpList = parser.readFeed()
            if (parser.isSuccessful) {
                newsDao.delete(dataList)
                dataList = tmpList
                android.os.Handler(Looper.getMainLooper()).post {
                    kotlin.run {
                        mAdapter.changeData(dataList)
                    }
                }
            }
        }
        Thread(task).start()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_recycler_view

        mAdapter = MainAdapter(dataList)

        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = mLayoutManager
    }
}