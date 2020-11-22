package com.example.rssparser.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

// @JvmOverloads constructor - решение со StackOverFlow
// Обязательно нужен конструктор по умолчанию (пустой)
// Энтити для Room
// Serializable для bundle
@Entity
@Root(name = "item")
data class NewsModel @JvmOverloads constructor(
    @field:Element(name = "guid")
    var guid: String = "empty",
    @field:Element(name = "title")
    var title: String = "",
    @PrimaryKey
    @field:Element(name = "link")
    var link: String = "empty",
    @field:Element(name = "description")
    var description: String = "",
    @Embedded
    @field:Element(name = "enclosure")
    var enclosure: Enclosure = Enclosure(),
    @field:Element(name = "pubDate")
    var pubDate: String = "",
    @field:Element(name = "category")
    var category: String = ""
) : Serializable