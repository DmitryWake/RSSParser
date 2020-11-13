package com.example.rssparser.screens.main_screen

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.APP_ACTIVITY
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainAdapter

    private var dataList: MutableList<NewsModel> = mutableListOf()

    override fun onStart() {
        super.onStart()
        dataList.add(NewsModel("Title", "123", "descipt"))
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_recycler_view

        mAdapter = MainAdapter(dataList)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(APP_ACTIVITY)
    }
}