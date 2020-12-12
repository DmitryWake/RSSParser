package com.example.rssparser.ui.activities

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.rssparser.app.App
import com.example.rssparser.databinding.ActivityMainBinding
import com.example.rssparser.ui.fragments.newslistscreen.NewsListFragment
import com.example.rssparser.utilities.replaceFragment

class MainActivity : MvpAppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mToolbar = mBinding.activityMainToolbar
        setSupportActionBar(mToolbar)

        if (savedInstanceState == null || savedInstanceState.isEmpty) {
            replaceFragment(this, NewsListFragment(), false)
        }
    }

    fun component() = App.appComponent
}