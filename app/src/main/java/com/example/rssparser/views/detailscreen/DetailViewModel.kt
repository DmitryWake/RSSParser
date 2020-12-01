package com.example.rssparser.views.detailscreen

import androidx.lifecycle.MutableLiveData
import com.example.rssparser.viewmodels.BaseNewsViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : BaseNewsViewModel() {

    val linkLiveData = MutableLiveData<String>()


    // Переход в браузер по ссылке
    fun onClickReadNextButton() {
        linkLiveData.value = mNewsModel.link
    }
}