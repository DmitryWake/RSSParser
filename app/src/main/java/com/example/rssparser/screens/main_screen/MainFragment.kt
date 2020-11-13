package com.example.rssparser.screens.main_screen

import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.models.NewsModel
import com.example.rssparser.rss.RSSParser
import com.example.rssparser.utilities.APP_ACTIVITY
import kotlinx.android.synthetic.main.fragment_main.*
import java.net.URL


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainAdapter

    private var dataList: List<NewsModel> = mutableListOf()

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        downloadData()
    }

    private fun downloadData() {
        val parser = RSSParser(URL("https://lenta.ru/rss/news"))

        val task = Runnable {
            dataList = parser.readFeed()
            android.os.Handler(Looper.getMainLooper()).post {
                kotlin.run {
                    mAdapter.changeData(dataList)
                }
            }
        }
        val thread = Thread(task)
        thread.start()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_recycler_view

        mAdapter = MainAdapter(dataList)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(APP_ACTIVITY)
    }
}