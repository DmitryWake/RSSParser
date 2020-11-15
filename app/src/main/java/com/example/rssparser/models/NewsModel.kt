package com.example.rssparser.models

import java.io.Serializable


data class NewsModel(
    var title: String = "",
    var description: String = "",
    var link: String = "empty",
    var guid: String = "empty",
    var imageUrl: String = "empty"
) : Serializable