package com.example.rssparser.view_models

import com.example.rssparser.utilities.replaceFragment
import com.example.rssparser.views.detail_screen.DetailFragment

class NewsItemViewModel: BaseNewsViewModel() {

    // При клике на элемент переход к его детальному представлению
    fun onClick() {
        replaceFragment(DetailFragment(mNewsModel), true)
    }
}