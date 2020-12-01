package com.example.rssparser.rss

import com.example.rssparser.models.NewsModel
import com.example.rssparser.rss.models.NewsModelApi

/**
 * @author Ovchinnikov Roman
 */
class ResponseMapper {

    fun toNewsModel(dto0: NewsModelApi?): NewsModel? {
        return if (dto0 != null) {
            val title = dto0.title
            val description = dto0.description
            val link = dto0.link
            val imageUrl = dto0.enclosure.url
            NewsModel(title, description, imageUrl, link)
        } else null
    }

    fun toNewsModelList(dtos: List<NewsModelApi>?): List<NewsModel> {
        val resultList = mutableListOf<NewsModel>()
        dtos?.forEach {
            resultList.add(toNewsModel(it)!!)
        }
        return resultList
    }
}