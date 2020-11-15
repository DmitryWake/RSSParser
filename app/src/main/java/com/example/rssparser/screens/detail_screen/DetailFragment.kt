package com.example.rssparser.screens.detail_screen

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rssparser.R
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.FRAGMENT_KEY
import com.example.rssparser.utilities.downloadAndSetImage
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment(private var newsModel: NewsModel) : Fragment(R.layout.fragment_detail) {

    companion object {
        const val NEWS_MODEL_TAG = "news_model"
    }

    private lateinit var mTitle: TextView
    private lateinit var mImageView: ImageView
    private lateinit var mDescription: TextView

    constructor(): this(NewsModel())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey(NEWS_MODEL_TAG)) {
            newsModel = savedInstanceState.getSerializable(NEWS_MODEL_TAG) as NewsModel
        }
    }

    override fun onStart() {
        super.onStart()
        initFields()
        drawNews()
    }

    private fun drawNews() {
        mTitle.text = newsModel.title
        mDescription.text = newsModel.description
        mImageView.downloadAndSetImage(newsModel.imageUrl)
    }

    private fun initFields() {
        mTitle = detail_title
        mImageView = detail_image
        mDescription = detail_description
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(NEWS_MODEL_TAG, newsModel)
    }

}