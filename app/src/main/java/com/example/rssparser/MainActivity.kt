package com.example.rssparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rssparser.databinding.ActivityMainBinding
import com.example.rssparser.room.AppDatabaseHelper
import com.example.rssparser.screens.main_screen.MainFragment
import com.example.rssparser.utilities.APP_ACTIVITY
import com.example.rssparser.utilities.APP_DATABASE_HELPER
import com.example.rssparser.utilities.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFields()
        if (savedInstanceState == null || savedInstanceState.isEmpty) {
            replaceFragment(MainFragment(), false)
        }
    }

    private fun initFields() {
        APP_ACTIVITY = this
        APP_DATABASE_HELPER = AppDatabaseHelper()
        APP_DATABASE_HELPER.initDatabase()
    }

}