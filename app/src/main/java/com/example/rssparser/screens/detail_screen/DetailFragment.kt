package com.example.rssparser.screens.detail_screen

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rssparser.R
import com.example.rssparser.models.NewsModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment(private val newsModel: NewsModel) : Fragment(R.layout.fragment_detail) {

    private lateinit var mTitle: TextView
    private lateinit var mImageView: ImageView
    private lateinit var mDescription: TextView

    override fun onStart() {
        super.onStart()
        initFields()
        drawNews()
    }

    private fun drawNews() {
        mTitle.text = newsModel.title
        mDescription.text = newsModel.description
    }

    private fun initFields() {
        mTitle = detail_title
        mImageView = detail_image
        mDescription = detail_description
    }

}