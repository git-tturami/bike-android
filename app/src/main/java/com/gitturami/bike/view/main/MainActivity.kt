package com.gitturami.bike.view.main

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gitturami.bike.R
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContact.View {
    private lateinit var presenter: MainContact.Presenter
    private lateinit var tMapView: TMapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutTmap = linearLayoutTmap
        tMapView = TMapView(this)

        tMapView.setSKTMapApiKey("3826adf1-c99a-4e69-95c5-3763539465ea")
        linearLayoutTmap.addView(tMapView)
        tMapView.setCenterPoint(126.9573662, 37.5048935)

        presenter = MainPresenter()
        presenter.takeView(this)
        presenter.test()
    }

    override fun findPath(start:TMapPoint, end:TMapPoint) {
        try {
            val data = TMapData()
            Log.d("gess","gess")
            data.findPathData(start, end) { path ->
                runOnUiThread {
                    path.lineWidth = 5f
                    path.lineColor = Color.BLUE
                    tMapView.addTMapPath(path)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}