package com.example.rssparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.rssparser.databinding.ActivityMainBinding
import com.example.rssparser.room.AppDatabase
import com.example.rssparser.room.dao.NewsDao
import com.example.rssparser.screens.main_screen.MainFragment
import com.example.rssparser.utilities.APP_ACTIVITY
import com.example.rssparser.utilities.replaceFragment

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var dataBase: AppDatabase
        lateinit var newsDao: NewsDao
    }

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        if (savedInstanceState == null || savedInstanceState.isEmpty) {
            replaceFragment(MainFragment(), false)
        }
        initDatabase()
    }

    private fun initDatabase() {
        dataBase = Room.databaseBuilder(APP_ACTIVITY, AppDatabase::class.java, "database").build()
        newsDao = dataBase.newsDao()
    }

}