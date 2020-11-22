package com.example.rssparser.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

// Модель самого RSS
// Нужно для SimpleXmlConverterFactory
// @JvmOverloads constructor - решение со StackOverFlow
// Обязательно нужен конструктор по умолчанию (пустой)
@Root(name = "rss", strict = false)
data class ArticleResponse @JvmOverloads constructor(
    @field:Element(name = "channel")
    var channel: ChannelModel = ChannelModel()
)