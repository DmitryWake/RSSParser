package com.example.rssparser.rss

import android.util.Log
import com.example.rssparser.models.NewsModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.lang.RuntimeException
import java.net.URL

class RSSParser(private val url: URL) {

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

    fun readFeed(): List<NewsModel> {

        val results = mutableListOf<NewsModel>()

        var isItems = false

        try {
            var newsModel = NewsModel()

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
                            ITEM -> {
                                newsModel = NewsModel()
                            }
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
                                newsModel.description = parser.text
                            }
                            ENCLOSURE -> {
                                for (i in 0 until parser.attributeCount) {
                                    if (parser.getAttributeName(i) == URL)
                                        newsModel.imageUrl = parser.getAttributeValue(i)
                                }
                            }
                        }
                    }
                } else if (parser.eventType == XmlPullParser.END_TAG && parser.name == ITEM) {
                    results.add(newsModel)
                }
                parser.next()
            }
        } catch (t: Throwable) {
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