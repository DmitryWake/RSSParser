package com.example.rssparser.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rssparser.database.room.entities.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM newsEntity WHERE link = :link")
    fun get(link: String): NewsEntity

    @Query("SELECT * FROM newsEntity")
    fun getAll(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataList: List<NewsEntity>)

    @Query("DELETE FROM newsEntity")
    fun deleteAll()
}