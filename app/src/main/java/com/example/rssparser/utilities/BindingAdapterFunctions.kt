package com.example.rssparser.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.rssparser.R
import com.squareup.picasso.Picasso

// Загружаем фото по url с помощью DataBinding
@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) {
    if (url != null && url.isNotEmpty())
        Picasso.get().load(url).fit().placeholder(R.drawable.ic_base_image).into(view)
}