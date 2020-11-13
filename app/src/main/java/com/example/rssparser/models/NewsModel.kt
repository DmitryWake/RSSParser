package com.example.rssparser.models

data class NewsModel(
    var title: String = "",
    var description: String = "",
    var link: String = "empty",
    var guid: String = "empty",
    var imageUrl: String = "empty"
)