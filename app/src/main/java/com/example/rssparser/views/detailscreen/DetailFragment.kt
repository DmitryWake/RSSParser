package com.example.rssparser.views.detailscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.rssparser.MainActivity
import com.example.rssparser.R
import com.example.rssparser.databinding.FragmentDetailBinding
import com.example.rssparser.models.NewsModel


class DetailFragment(private var newsModel: NewsModel) : Fragment(R.layout.fragment_detail) {

    companion object {
        // Тег для вывода в консоль
        const val NEWS_MODEL_TAG = "news_model"
    }

    private lateinit var mViewModel: DetailViewModel

    private lateinit var component: DetailFragmentSubcomponent

    constructor() : this(NewsModel())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component =
            (activity as MainActivity).component().getMainActivitySubcomponent().detailComponent()
        initViewModel(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        binding.viewModel = mViewModel
        mViewModel.apply {
            linkLiveData.observe({ viewLifecycleOwner.lifecycle }, ::readNext)
        }
        return binding.root
    }

    private fun readNext(link: String?) {
        if (!link.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            ContextCompat.startActivity(activity as MainActivity, intent, null)
        }
    }

    override fun onStart() {
        super.onStart()
        val activity = activity as MainActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.mToolbar.setNavigationOnClickListener {
            activity.supportFragmentManager.popBackStack()
        }
    }

    private fun initViewModel(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this, component.viewModelFactory())
            .get(DetailViewModel::class.java)
        if (savedInstanceState != null && savedInstanceState.containsKey(NEWS_MODEL_TAG))
            newsModel = savedInstanceState.getSerializable(NEWS_MODEL_TAG) as NewsModel
        mViewModel.initViewModel(newsModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(NEWS_MODEL_TAG, newsModel)
    }

}