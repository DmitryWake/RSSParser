package com.example.rssparser.room.dao

import androidx.room.*
import com.example.rssparser.models.NewsModel

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsmodel")
    fun getAll(): List<NewsModel>

    @Delete
    fun delete(dataList: List<NewsModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataList: List<NewsModel>)
}