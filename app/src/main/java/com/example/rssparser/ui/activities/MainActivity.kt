package com.example.rssparser.ui.activities

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.rssparser.app.App
import com.example.rssparser.databinding.ActivityMainBinding
import com.example.rssparser.ui.fragments.newslistscreen.NewsListFragment
import com.example.rssparser.utilities.replaceFragment
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mToolbar = mBinding.activityMainToolbar
        setSupportActionBar(mToolbar)
    }

    fun component() = App.appComponent

    override fun showNewsListFragment() {
        replaceFragment(this, NewsListFragment(), false)
    }
}