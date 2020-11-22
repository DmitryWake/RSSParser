package com.example.rssparser.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.example.rssparser.R
import com.squareup.picasso.Picasso

fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
    if (addToBackStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }
}

// Загружаем фото по url с помощью DataBinding
@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String) {
    Picasso.get().load(url).fit().placeholder(R.drawable.ic_base_image).into(view)
}

// У загружаемого описания первый символ \n
// Чтобы не было пустых мест - форматируем
fun String.formatDescription(): String {
    return this.substring(1)
}