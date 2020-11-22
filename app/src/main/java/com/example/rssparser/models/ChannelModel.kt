package com.example.rssparser.models

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

// @JvmOverloads constructor - решение со StackOverFlow
// Обязательно нужен конструктор по умолчанию (пустой)
@Root(name = "channel", strict = false)
data class ChannelModel @JvmOverloads constructor(
    @field:ElementList(inline = true, name = "item")
    var newsList: MutableList<NewsModel> = mutableListOf()
)