package com.example.rssparser.ui.fragments.detailscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.rssparser.R
import com.example.rssparser.models.NewsModel
import com.example.rssparser.ui.activities.MainActivity
import com.example.rssparser.ui.fragments.detailscreen.presenter.DetailPresenter
import com.example.rssparser.ui.fragments.detailscreen.view.DetailView
import com.example.rssparser.utilities.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*


class DetailFragment : MvpAppCompatFragment(), DetailView {

    private var link: String = ""

    @InjectPresenter
    lateinit var detailPresenter: DetailPresenter

    @ProvidePresenter
    fun provideDetailPresenter(): DetailPresenter {
        val component =
            (activity as MainActivity).component().getMainActivitySubcomponent().detailComponent()
        return component.getDetailPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null && arguments!!.containsKey(NEWS_LINK_TAG))
            link = arguments!!.getString(NEWS_LINK_TAG)!!

        detailPresenter.loadNewsFromDatabase(link)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_detail,
            container,
            false
        )

        (activity as MainActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            mToolbar.setNavigationOnClickListener {
                supportFragmentManager.popBackStack()
            }
        }

        view.fragment_detail_b_go_to_browser.setOnClickListener {
            goToBrowser(link)
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NEWS_LINK_TAG, link)
    }

    private fun goToBrowser(link: String?) {
        if (!link.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            ContextCompat.startActivity(activity as MainActivity, intent, null)
        }
    }

    override fun updateView(newsModel: NewsModel) {
        fragment_detail_tv_title.text = newsModel.title
        fragment_detail_tv_description.text = newsModel.description
        loadImage(fragment_detail_iv_image, newsModel.imageUrl)
    }

    override fun showError(message: String) {
        Log.e(TAG, message)
    }

    companion object {
        const val TAG = "DetailFragment"
        const val NEWS_LINK_TAG = "news_link"

        fun newInstance(link: String) = DetailFragment().apply {
            arguments = Bundle().apply { putString(NEWS_LINK_TAG, link) }
        }
    }
}