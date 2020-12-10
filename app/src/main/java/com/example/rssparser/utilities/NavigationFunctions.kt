package com.example.rssparser.utilities

import androidx.fragment.app.Fragment
import com.example.rssparser.R
import com.example.rssparser.ui.activities.MainActivity

fun replaceFragment(context: MainActivity, fragment: Fragment, addToBackStack: Boolean) {
    if (addToBackStack) {
        context.supportFragmentManager.beginTransaction()
            .replace(R.id.activity_main_cl_fragment_container, fragment).addToBackStack(null)
            .commit()
    } else {
        context.supportFragmentManager.beginTransaction()
            .replace(R.id.activity_main_cl_fragment_container, fragment).commit()
    }
}