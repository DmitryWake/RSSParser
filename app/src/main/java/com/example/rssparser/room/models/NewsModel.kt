package com.example.rssparser.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class NewsModel(
    var title: String = "",
    var description: String = "",
    @PrimaryKey
    var link: String = "empty",
    var guid: String = "empty",
    var imageUrl: String = "empty"
) : Serializable