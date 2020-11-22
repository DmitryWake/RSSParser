package com.example.rssparser.view_models

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.rssparser.utilities.APP_ACTIVITY

class DetailViewModel: BaseNewsViewModel() {

    // Переход в браузер по ссылке
    fun onClickReadNextButton() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mNewsModel.link))
        startActivity(APP_ACTIVITY, intent, null)
    }
}