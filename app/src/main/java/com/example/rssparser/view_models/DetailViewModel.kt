package com.example.rssparser.view_models

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.rssparser.MainActivity

class DetailViewModel: BaseNewsViewModel() {

    // Переход в браузер по ссылке
    fun onClickReadNextButton() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mNewsModel.link))
        startActivity(MainActivity.context, intent, null)
    }
}