package com.example.rssparser.views.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.MainActivity
import com.example.rssparser.R
import com.example.rssparser.databinding.FragmentNewslistBinding
import com.example.rssparser.models.NewsModel
import com.example.rssparser.views.main_screen.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_newslist.*


class NewsListFragment : Fragment(R.layout.fragment_newslist) {

    companion object {
        // Храним позицию recycler view
        var mRecyclerViewPosition: Int = 0
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private val adapter = MainAdapter()
    private lateinit var mViewModel: NewsListViewModel

    private lateinit var component: NewsListFragmentSubcomponent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentNewslistBinding>(
            inflater,
            R.layout.fragment_newslist,
            container,
            false
        )
        binding.viewModel = mViewModel
        mViewModel.apply {
            newsListLiveData.observe({ viewLifecycleOwner.lifecycle }, ::setItems)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity as MainActivity).component().getMainActivitySubcomponent().newsListComponent()
        mViewModel = ViewModelProviders.of(this, component.viewModelFactory()).get(NewsListViewModel::class.java)
        // Вешаем слушателя, который будет вызывать нужные методы
        lifecycle.addObserver(mViewModel)
    }

    private fun setItems(items: List<NewsModel>) {
        adapter.changeData(items)
    }

    override fun onStart() {
        super.onStart()
        mLayoutManager = LinearLayoutManager(this.context)
        initRecyclerView()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onResume() {
        super.onResume()
        mRecyclerView.scrollToPosition(mRecyclerViewPosition)
    }

    override fun onPause() {
        super.onPause()
        // Сохраняем позицию RecyclerView
        mRecyclerViewPosition = mLayoutManager.findFirstVisibleItemPosition()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_recycler_view
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = mLayoutManager
    }
}