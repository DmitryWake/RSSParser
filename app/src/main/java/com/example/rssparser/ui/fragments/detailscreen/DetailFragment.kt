package com.example.rssparser.ui.fragments.detailscreen

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
import com.example.rssparser.R
import com.example.rssparser.databinding.FragmentDetailBinding
import com.example.rssparser.models.NewsModel
import com.example.rssparser.ui.activities.MainActivity
import com.example.rssparser.utilities.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var viewModel: DetailViewModel
    private lateinit var component: DetailFragmentSubcomponent
    private var link: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component =
            (activity as MainActivity).component().getMainActivitySubcomponent().detailComponent()

        viewModel = ViewModelProviders.of(this, component.viewModelFactory())
            .get(DetailViewModel::class.java)

        if (arguments != null && arguments!!.containsKey(NEWS_LINK_TAG))
            link = arguments!!.getString(NEWS_LINK_TAG)!!

        viewModel.initViewModel(link)

        lifecycle.addObserver(viewModel)
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
        binding.viewModel = viewModel
        viewModel.apply {
            linkLiveData.observe({ viewLifecycleOwner.lifecycle }, ::readNext)
            newsModelLiveData.observe({ viewLifecycleOwner.lifecycle }, ::updateUI)
        }

        val activity = activity as MainActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.mToolbar.setNavigationOnClickListener {
            activity.supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun readNext(link: String?) {
        if (!link.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            ContextCompat.startActivity(activity as MainActivity, intent, null)
        }
    }

    private fun updateUI(newsModel: NewsModel) {
        detail_title.text = newsModel.title
        detail_description.text = newsModel.description
        loadImage(detail_image, newsModel.imageUrl)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NEWS_LINK_TAG, link)
    }

    companion object {
        // Тег для вывода в консоль
        const val NEWS_LINK_TAG = "news_link"

        fun newInstance(link: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(NEWS_LINK_TAG, link)
            fragment.arguments = args
            return fragment
        }
    }

}