package com.gitturami.bike.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gitturami.bike.R
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import  net.daum.mf.map.api.MapLayout
//import net.daum.mf.map.api.MapView



class MainActivity : AppCompatActivity(), MainContact.View {
    private lateinit var presenter : MainContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val mapView= MapView(this)
//////        //if do not use this, black screen come out
//        mapView.setDaumMapApiKey("e3f875ab88a86245f59196ce3dbc8bf5")
//        val mapViewContainer=map_view
//        mapViewContainer.addView(mapView)
//        mapViewContainer.removeAllViews()



        presenter = MainPresenter()
        presenter.takeView(this)

    }


}