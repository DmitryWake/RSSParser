package com.example.rssparser.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class NewsModel(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "empty",
    @PrimaryKey
    val link: String = "empty"
) : Serializable
