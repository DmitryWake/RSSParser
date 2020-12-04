package com.example.rssparser.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsEntity")
data class NewsEntity(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "empty",
    @PrimaryKey()
    val link: String = ""
)
