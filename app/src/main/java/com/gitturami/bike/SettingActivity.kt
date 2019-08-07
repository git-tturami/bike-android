package com.gitturami.bike

import android.content.Intent
import android.drm.DrmStore
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)
        setSupportActionBar(app_toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        exitSettingActivity()
    }

    fun exitSettingActivity() {
        backButton.setOnClickListener {
            finish()
        }
    }
}

