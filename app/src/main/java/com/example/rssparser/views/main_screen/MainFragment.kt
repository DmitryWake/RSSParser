package com.example.rssparser.views.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.R
import com.example.rssparser.databinding.FragmentMainBinding
import com.example.rssparser.utilities.APP_ACTIVITY
import com.example.rssparser.view_models.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        // Храним позицию recycler view
        var mRecyclerViewPosition: Int = 0
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var mViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        binding.viewModel = mViewModel
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = MainViewModel()
        // Вешаем слушателя, который будет вызывать нужные методы
        lifecycle.addObserver(mViewModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mViewModel.saveData()
    }

    override fun onStart() {
        super.onStart()
        mLayoutManager = LinearLayoutManager(this.context)
        initRecyclerView()
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        mRecyclerView.adapter = mViewModel.mAdapter
        mRecyclerView.layoutManager = mLayoutManager
    }
}