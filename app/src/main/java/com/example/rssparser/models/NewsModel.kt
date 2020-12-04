package com.example.rssparser.models


data class NewsModel(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "empty",
    val link: String = "empty"
)
