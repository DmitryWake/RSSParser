package com.example.rssparser.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class ArticleResponse @JvmOverloads constructor(
    @field:Element(name = "channel")
    var channel: ChannelModel = ChannelModel()
)