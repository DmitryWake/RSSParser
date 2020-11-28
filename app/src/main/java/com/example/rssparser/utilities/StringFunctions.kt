package com.example.rssparser.utilities

// У загружаемого описания первый символ \n
// Чтобы не было пустых мест - форматируем
fun String.formatDescription(): String {
    return this.substring(1)
}