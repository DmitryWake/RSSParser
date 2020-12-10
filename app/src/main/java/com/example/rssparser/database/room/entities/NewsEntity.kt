package com.example.rssparser.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsEntity")
data class NewsEntity(
    @PrimaryKey
    val link: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "empty"
)
