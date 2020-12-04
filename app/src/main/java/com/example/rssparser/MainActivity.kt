package com.example.rssparser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.rssparser.databinding.ActivityMainBinding
import com.example.rssparser.utilities.replaceFragment
import com.example.rssparser.views.newslistscreen.NewsListFragment

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var context: MainActivity
    }

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFields()
        setSupportActionBar(mToolbar)
        if (savedInstanceState == null || savedInstanceState.isEmpty) {
            replaceFragment(this, NewsListFragment(), false)
        }
    }

    fun component() = App.appComponent

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        context = this
    }

}