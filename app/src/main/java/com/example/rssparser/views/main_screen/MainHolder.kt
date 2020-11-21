package com.example.rssparser.views.main_screen

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparser.models.NewsModel
import com.example.rssparser.views.detail_screen.DetailFragment
import com.example.rssparser.utilities.downloadAndSetImage
import com.example.rssparser.utilities.replaceFragment
import kotlinx.android.synthetic.main.news_item.view.*

class MainHolder(view: View): RecyclerView.ViewHolder(view) {

    private val mItemContainer: ConstraintLayout = view.news_item_container
    private val mImageView: ImageView = view.news_image
    private val mTitle: TextView = view.news_title
    private val mDescription: TextView = view.news_description


    fun drawItem(newsModel: NewsModel) {
        mTitle.text = newsModel.title
        mDescription.text = newsModel.description
        mImageView.downloadAndSetImage(newsModel.enclosure.url)
        mItemContainer.setOnClickListener {
            replaceFragment(DetailFragment(newsModel), true)
        }
    }
}