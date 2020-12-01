package com.example.rssparser.views.detailscreen

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.rssparser.MainActivity
import com.example.rssparser.viewmodels.BaseNewsViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : BaseNewsViewModel() {

    // Переход в браузер по ссылке
    fun onClickReadNextButton() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mNewsModel.link))
        startActivity(MainActivity.context, intent, null)
    }
}