package com.example.rssparser.database.room.dao

import androidx.room.*
import com.example.rssparser.database.room.entities.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM newsEntity WHERE link = :link")
    fun get(link: String): NewsEntity

    @Query("SELECT * FROM newsEntity")
    fun getAll(): List<NewsEntity>

    @Delete
    fun delete(dataList: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataList: List<NewsEntity>)

    @Query("DELETE FROM newsEntity")
    fun deleteAll()
}