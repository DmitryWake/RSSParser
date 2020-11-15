package com.example.rssparser.screens.main_screen

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.MainActivity
import com.example.rssparser.R
import com.example.rssparser.room.models.NewsModel
import com.example.rssparser.rss.RSSParser
import com.example.rssparser.utilities.APP_ACTIVITY
import kotlinx.android.synthetic.main.fragment_main.*
import java.net.URL


class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        private var dataList: List<NewsModel> = mutableListOf()
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (dataList.isEmpty())
            loadFromDatabase()
    }

    override fun onStart() {
        super.onStart()
        downloadData()
        initRecyclerView()
    }

    override fun onStop() {
        super.onStop()
        saveToDatabase()
    }

    private fun saveToDatabase() {
        val task = Runnable {
            MainActivity.newsDao.insert(dataList)
        }
        Thread(task).start()
    }

    private fun loadFromDatabase() {
        val task = Runnable {
            dataList = MainActivity.newsDao.getAll()
            println()
            android.os.Handler(Looper.getMainLooper()).post {
                kotlin.run {
                    mAdapter.changeData(dataList)
                }
            }
        }
        Thread(task).start()
    }


    private fun downloadData() {
        val parser = RSSParser(URL("https://lenta.ru/rss/news"))

        val task = Runnable {
            val tmpList = parser.readFeed()
            if (parser.isSuccessful) {
                MainActivity.newsDao.delete(dataList)
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

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(APP_ACTIVITY)
    }
}