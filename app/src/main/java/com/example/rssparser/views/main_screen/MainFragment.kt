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
import com.example.rssparser.view_models.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val TAG = "MainFragment"
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
        mViewModel.initViewModel()
    }

    override fun onStart() {
        super.onStart()
        mLayoutManager = LinearLayoutManager(this.context)
        initRecyclerView()
        mRecyclerView.scrollToPosition(mRecyclerViewPosition)
    }

    override fun onStop() {
        super.onStop()
        mViewModel.saveData()
    }

    override fun onPause() {
        super.onPause()
        mRecyclerViewPosition = mLayoutManager.findFirstVisibleItemPosition()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_recycler_view
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.adapter = mViewModel.mAdapter
        mRecyclerView.layoutManager = mLayoutManager
    }
}