package com.gitturami.bike.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gitturami.bike.R
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import  net.daum.mf.map.api.MapLayout
import net.daum.mf.map.api.MapView
import android.provider.SyncStateContract.Helpers.update
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity(), MainContact.View {
    private lateinit var presenter : MainContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val mapLayout = MapLayout(this)
//        mTaxiMap = TaxiMap(mapLayout.mapView)
//        val mapLayout = MapLayout(this)
//        map_view.addView(mapLayout)

//        val mapView= MapVi
//        mapViewContainer.removeAllViews()



        presenter = MainPresenter()
        presenter.takeView(this)

        try {
            val info = packageManager.getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("MY KEY HASH:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

    }


}