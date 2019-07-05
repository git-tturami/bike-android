package com.gitturami.bike

import android.app.Application
import android.content.Context
import android.content.Intent
import com.gitturami.bike.view.intro.IntroActivity

class BikeApplication : Application() {
    companion object {
        lateinit var instance: BikeApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        var intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
    }
}