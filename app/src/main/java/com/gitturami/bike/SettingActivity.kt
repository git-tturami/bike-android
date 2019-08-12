package com.gitturami.bike

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
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

