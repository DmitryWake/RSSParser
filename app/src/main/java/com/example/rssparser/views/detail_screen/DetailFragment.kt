package com.example.rssparser.views.detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.rssparser.R
import com.example.rssparser.databinding.FragmentDetailBinding
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.APP_ACTIVITY
import com.example.rssparser.view_models.DetailViewModel


class DetailFragment(private var newsModel: NewsModel) : Fragment(R.layout.fragment_detail) {

    companion object {
        // Тег для вывода в консоль
        const val NEWS_MODEL_TAG = "news_model"
    }

    private lateinit var mViewModel: DetailViewModel

    constructor() : this(NewsModel())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        binding.viewModel = mViewModel
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    // Возможно сделал неправильно и не стоит снова инициализировать
    // ViewModel после поворота
    private fun initViewModel(savedInstanceState: Bundle?) {
        mViewModel = DetailViewModel()
        if (savedInstanceState != null && savedInstanceState.containsKey(NEWS_MODEL_TAG))
            newsModel = savedInstanceState.getSerializable(NEWS_MODEL_TAG) as NewsModel
        mViewModel.initViewModel(newsModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(NEWS_MODEL_TAG, newsModel)
    }

}