package com.example.rssparser.utilities

import androidx.fragment.app.Fragment
import com.example.rssparser.R

fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
    if (addToBackStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }
}