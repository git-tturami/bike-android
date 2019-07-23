package com.gitturami.bike.view.main

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.LinearLayout
import com.gitturami.bike.R
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine
import com.skt.Tmap.TMapView

class MainActivity : AppCompatActivity(), MainContact.View {
    private lateinit var presenter: MainContact.Presenter
    //private lateinit var tMapView:TMapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutTmap = findViewById(R.id.linearLayoutTmap) as LinearLayout
        val tMapView = TMapView(this)

        tMapView.setSKTMapApiKey("3826adf1-c99a-4e69-95c5-3763539465ea")
        linearLayoutTmap.addView(tMapView)

        tMapView.setCenterPoint(126.9573662, 37.5048935)

        val tMapPointStart = TMapPoint(37.5048935, 126.9573662) // 중앙대학교
        val tMapPointEnd = TMapPoint(37.5099724, 126.9949061) // 반포한강공원

        findpath(tMapPointStart,tMapPointEnd)

        presenter = MainPresenter()
        presenter.takeView(linearLayoutTmap)
    }

//    fun start() {
//
//
//    }

    fun findpath(start:TMapPoint, end:TMapPoint){
        try {
            val data = TMapData()
            Log.d("gess","gess")
            data.findPathData(start, end, object: TMapData.FindPathDataListenerCallback {
                override fun onFindPathData(path: TMapPolyLine){
                    runOnUiThread(object: Runnable {
                        override fun run(){
                            path.setLineWidth(5f)
                            path.setLineColor(Color.BLUE)
                            //tMapView.addTMapPath(path)
                        }
                    })
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}