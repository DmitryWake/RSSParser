package com.example.rssparser.rss.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

// @JvmOverloads constructor - решение со StackOverFlow
// Обязательно нужен конструктор по умолчанию (пустой)
@Root(name = "item")
data class NewsModelApi @JvmOverloads constructor(
    @field:Element(name = "guid")
    var guid: String = "empty",
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "link")
    var link: String = "empty",
    @field:Element(name = "description")
    var description: String = "",
    @field:Element(name = "enclosure")
    var enclosure: Enclosure = Enclosure(),
    @field:Element(name = "pubDate")
    var pubDate: String = "",
    @field:Element(name = "category")
    var category: String = ""
)