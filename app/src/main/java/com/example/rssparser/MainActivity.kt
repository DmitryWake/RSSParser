package com.example.rssparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.rssparser.databinding.ActivityMainBinding
import com.example.rssparser.utilities.APP
import com.example.rssparser.views.main_screen.MainFragment
import com.example.rssparser.utilities.APP_ACTIVITY
import com.example.rssparser.utilities.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFields()
        setSupportActionBar(mToolbar)
        if (savedInstanceState == null || savedInstanceState.isEmpty) {
            replaceFragment(MainFragment(), false)
        }
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        // Так как у нас архитектура одного активити
        // Делаем так, чтобы ссылка всегда была под рукой
        APP_ACTIVITY = this
        // Когда получили ссылку, проводим инициализацию базы данных
        APP.appNewsRepository.initDatabase()
    }

}