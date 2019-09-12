package com.gitturami.bike.view.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.gitturami.bike.R
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

    private fun exitSettingActivity() {
        backButton.setOnClickListener {
            finish()
        }
    }
}

