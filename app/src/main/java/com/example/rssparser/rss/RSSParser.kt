package com.example.rssparser.rss

import android.util.Log
import com.example.rssparser.models.NewsModel
import com.example.rssparser.utilities.formatDescription
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.lang.RuntimeException
import java.net.URL

// Первая версия RSS парсера
class RSSParser(private val url: URL) {

    // Нужные теги
    companion object {
        const val TAG = "RSSParser"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val URL = "url"
        const val LINK = "link"
        const val ITEM = "item"
        const val GUID = "guid"
        const val ENCLOSURE = "enclosure"
    }

    // Успешная ли загрузка
    var isSuccessful: Boolean = true

    fun readFeed(): List<NewsModel> {

        val results = mutableListOf<NewsModel>()

        // Проверка: находимся ли мы в теге <item> ... </item>
        var isItems = false

        try {
            var newsModel = NewsModel()

            // Инициализация парсера
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()

            val stream = openStream()
            parser.setInput(stream, null)

            while (parser.eventType != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType == XmlPullParser.START_TAG) {
                    if (parser.name == ITEM) {
                        isItems = true
                    }
                    if (isItems) {
                        when (parser.name) {
                            // Если тег item
                            ITEM -> {
                                newsModel = NewsModel()
                            }
                            // Если тег guid и т.д.
                            GUID -> {
                                parser.next()
                                newsModel.guid = parser.text
                            }
                            TITLE -> {
                                parser.next()
                                newsModel.title = parser.text
                            }
                            LINK -> {
                                parser.next()
                                newsModel.link = parser.text
                            }
                            DESCRIPTION -> {
                                parser.next()
                                newsModel.description = parser.text.formatDescription()
                            }
                            ENCLOSURE -> {
                                // Ищем атрибут url
                                for (i in 0 until parser.attributeCount) {
                                    if (parser.getAttributeName(i) == URL)
                                        newsModel.enclosure.url = parser.getAttributeValue(i)
                                }
                            }
                        }
                    }
                    // Если встретили закрывающий тег Item
                } else if (parser.eventType == XmlPullParser.END_TAG && parser.name == ITEM) {
                    results.add(newsModel)
                }
                parser.next()
            }
        } catch (t: Throwable) {
            isSuccessful = false
            Log.e(TAG, "Loading error: ${t.message.toString()}")
        }
        return results
    }

    private fun openStream(): InputStream {
        try {
            return url.openStream()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

}