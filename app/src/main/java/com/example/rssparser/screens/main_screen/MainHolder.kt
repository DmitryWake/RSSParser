package com.example.rssparser.screens.main_screen

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.models.NewsModel
import kotlinx.android.synthetic.main.news_item.view.*

class MainHolder(view: View): RecyclerView.ViewHolder(view) {

    private val mImageView: ImageView = view.news_image
    private val mTitle: TextView = view.news_title
    private val mDescription: TextView = view.news_description


    fun drawItem(newsModel: NewsModel) {
        mTitle.text = newsModel.title
        mDescription.text = newsModel.description
    }
}