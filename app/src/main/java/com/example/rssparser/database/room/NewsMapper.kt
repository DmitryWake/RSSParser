package com.example.rssparser.database.room

import com.example.rssparser.database.room.entities.NewsEntity
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.formatDescription

class NewsMapper {
    fun toNewsModel(dto0: NewsEntity): NewsModel {
        val title = dto0.title
        val description = dto0.description.formatDescription()
        val link = dto0.link
        val imageUrl = dto0.imageUrl
        return NewsModel(title, description, imageUrl, link)
    }

    fun toNewsModelList(dtos: List<NewsEntity>?): List<NewsModel> {
        val result = mutableListOf<NewsModel>()
        if (!dtos.isNullOrEmpty()) {
            dtos.forEach {
                result.add(toNewsModel(it))
            }
        }
        return result
    }


    fun toNewsEntity(dto0: NewsModel): NewsEntity {
        val title = dto0.title
        val description = dto0.description.formatDescription()
        val link = dto0.link
        val imageUrl = dto0.imageUrl
        return NewsEntity(
            link = link,
            title = title,
            description = description,
            imageUrl = imageUrl
        )
    }

    fun toNewsEntityList(dtos: List<NewsModel>?): List<NewsEntity> {
        val result = mutableListOf<NewsEntity>()
        if (!dtos.isNullOrEmpty()) {
            dtos.forEach {
                result.add(toNewsEntity(it))
            }
        }
        return result
    }
}