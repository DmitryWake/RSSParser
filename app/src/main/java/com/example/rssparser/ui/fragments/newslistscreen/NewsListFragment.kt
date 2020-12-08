package com.example.rssparser.ui.fragments.newslistscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.databinding.FragmentNewslistBinding
import com.example.rssparser.models.NewsModel
import com.example.rssparser.ui.activities.MainActivity
import com.example.rssparser.ui.fragments.detailscreen.DetailFragment
import com.example.rssparser.ui.fragments.newslistscreen.adapter.NewsListAdapter
import com.example.rssparser.utilities.replaceFragment
import kotlinx.android.synthetic.main.fragment_newslist.*


class NewsListFragment : Fragment(R.layout.fragment_newslist) {

    companion object {
        // Храним позицию recycler view
        private var recyclerViewPosition: Int = 0
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val newsListAdapter = NewsListAdapter()
    private lateinit var viewModel: NewsListViewModel

    private lateinit var component: NewsListFragmentSubcomponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component =
            (activity as MainActivity).component().getMainActivitySubcomponent().newsListComponent()
        viewModel = ViewModelProviders.of(this, component.viewModelFactory())
            .get(NewsListViewModel::class.java)
        // Вешаем слушателя, который будет вызывать нужные методы
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentNewslistBinding>(
            inflater,
            R.layout.fragment_newslist,
            container,
            false
        )

        binding.viewModel = viewModel
        viewModel.apply {
            newsListLiveData.observe({ viewLifecycleOwner.lifecycle }, ::setItems)
            isRefreshingLiveData.observe({ viewLifecycleOwner.lifecycle }, ::changeRefreshing)
            isEmptyLiveData.observe({ viewLifecycleOwner.lifecycle }, ::changeListVisibility)
        }

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Инициализация RecyclerView и его компонентов
        recyclerView = binding.mainRecyclerView
        recyclerView.apply {
            adapter = newsListAdapter.apply {
                onItemClick = { url ->
                    replaceFragment(activity as MainActivity, DetailFragment(url), true)
                }
            }
            linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = false
        }

        return binding.root
    }

    private fun setItems(items: List<NewsModel>) {
        newsListAdapter.changeData(items)
    }

    private fun changeListVisibility(isEmpty: Boolean) {
        if (isEmpty) {
            empty_list_text.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            empty_list_text.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun changeRefreshing(isRefreshing: Boolean) {
        swipe_refresh_layout.isRefreshing = isRefreshing
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
}