package com.example.rssparser.models

import org.simpleframework.xml.Attribute

// @JvmOverloads constructor - решение со StackOverFlow
// Обязательно нужен конструктор по умолчанию (пустой)
data class Enclosure @JvmOverloads constructor(
    @field:Attribute(name = "url")
    var url: String = "empty",
    @field:Attribute(name = "length")
    var length: Long = 0,
    @field:Attribute(name = "type")
    var type: String = "empty"
)