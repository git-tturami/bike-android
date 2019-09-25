package com.gitturami.bike.view.intro

import android.Manifest
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.intro.presenter.IntroContact
import com.gitturami.bike.view.intro.presenter.IntroPresenter
import com.gitturami.bike.view.main.MainActivity

class IntroActivity : AppCompatActivity(), IntroContact.View {
    companion object {
        const val TAG = "IntroActivity"
    }
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var presenter: IntroContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        presenter = IntroPresenter()
        presenter.takeView(this)
        checkPermission()
    }

    override fun start() {
        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent.addFlags(FLAG_ACTIVITY_NEW_TASK))
        this.finish()
    }

    private fun checkPermission() {
        if (!checkLocationPermission()) {
            Logger.i(TAG, "need permission")
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQUEST_LOCATION_PERMISSION)
        } else {
            presenter.changeActivity()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Logger.i(TAG, "permission granted")
                    presenter.changeActivity()
                } else {
                    Logger.i(TAG, "permission denied")
                }
                return
            }
        }
    }
}
