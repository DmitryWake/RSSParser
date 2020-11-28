package com.example.rssparser.utilities

import androidx.fragment.app.Fragment
import com.example.rssparser.MainActivity
import com.example.rssparser.R

fun replaceFragment(context: MainActivity, fragment: Fragment, addToBackStack: Boolean) {
    if (addToBackStack) {
        context.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    } else {
        context.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }
}