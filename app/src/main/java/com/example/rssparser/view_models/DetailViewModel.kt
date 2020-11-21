package com.example.rssparser.view_models

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.APP_ACTIVITY

class DetailViewModel: ViewModel() {

    lateinit var mNewsModel: NewsModel

    fun onClickReadNextButton() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mNewsModel.link))
        startActivity(APP_ACTIVITY, intent, null)
    }

    fun initViewModel(newsModel: NewsModel) {
        mNewsModel = newsModel
    }

}