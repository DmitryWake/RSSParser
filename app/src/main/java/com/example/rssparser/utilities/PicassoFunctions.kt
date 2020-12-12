package com.example.rssparser.utilities

import android.widget.ImageView
import com.example.rssparser.R
import com.squareup.picasso.Picasso

fun loadImage(view: ImageView, url: String?) {
    if (url != null && url.isNotEmpty()) {
        Picasso.get().load(url).fit().placeholder(R.drawable.ic_base_image).into(view)
    }
}