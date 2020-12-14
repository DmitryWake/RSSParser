package com.example.rssparser.ui.fragments.newslistscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.models.NewsModel
import com.example.rssparser.ui.activities.MainActivity
import com.example.rssparser.ui.fragments.detailscreen.DetailFragment
import com.example.rssparser.ui.fragments.newslistscreen.adapter.NewsListAdapter
import com.example.rssparser.ui.fragments.newslistscreen.presenter.NewsListPresenter
import com.example.rssparser.ui.fragments.newslistscreen.view.NewsListView
import com.example.rssparser.utilities.replaceFragment
import kotlinx.android.synthetic.main.fragment_newslist.*
import kotlinx.android.synthetic.main.fragment_newslist.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class NewsListFragment : MvpAppCompatFragment(), NewsListView {

    companion object {
        // Храним позицию recycler view
        private var recyclerViewPosition: Int = 0
        const val TAG = "NewsListFragment"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val newsListAdapter = NewsListAdapter()

    @InjectPresenter
    lateinit var newsListPresenter: NewsListPresenter

    @ProvidePresenter
    fun provideNewsListPresenter(): NewsListPresenter {
        val component =
            (activity as MainActivity).component().getMainActivitySubcomponent().newsListComponent()
        return component.getNewsListPresenter()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_newslist,
            container,
            false
        )

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Инициализация RecyclerView и его компонентов
        recyclerView = view.fragment_newslist_rv_newslist
        recyclerView.apply {

            adapter = newsListAdapter.apply {
                onItemClick = { url ->
                    replaceFragment(activity as MainActivity, DetailFragment.newInstance(url), true)
                }
            }

            linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager

            val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            dividerItemDecoration.setDrawable(
                resources.getDrawable(R.drawable.divider_drawable)
            )
            addItemDecoration(dividerItemDecoration)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        fragment_newslist_swipe_refresh_layout.setOnRefreshListener {
            newsListPresenter.loadNews()
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.scrollToPosition(recyclerViewPosition)
    }

    override fun onPause() {
        super.onPause()
        // Сохраняем позицию RecyclerView
        recyclerViewPosition = linearLayoutManager.findFirstVisibleItemPosition()
    }

    private fun changeListVisibility(isEmpty: Boolean) {
        if (isEmpty) {
            fragment_newslist_tv_empty_list.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            fragment_newslist_tv_empty_list.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun showError(message: String) {
        Log.e(TAG, message)
    }

    override fun onStartLoading() {
        fragment_newslist_swipe_refresh_layout.isRefreshing = true
    }

    override fun onFinishLoading() {
        fragment_newslist_swipe_refresh_layout.isRefreshing = false
    }

    override fun updateView(newsList: List<NewsModel>) {
        changeListVisibility(newsList.isEmpty())
        newsListAdapter.changeData(newsList)
    }
}