package com.gitturami.bike.view.main

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.R


import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.*
import java.util.ArrayList
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint





class MainActivity : AppCompatActivity(), MainContact.View{
    private lateinit var presenter: MainContact.Presenter
    private lateinit var tMapView:TMapView
//    private lateinit var bottomSheet : BottomSheetBehavior<CoordinatorLayout>
    var apiKey = "3826adf1-c99a-4e69-95c5-3763539465ea"
    var mpoint: TMapPoint? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutTmap: LinearLayout = findViewById(R.id.linearLayoutTmap) as LinearLayout
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey(apiKey)

        val bottomSheet = findViewById(R.id.Bottom_Sheet) as CoordinatorLayout
        val bottomSheetBehavior = BottomSheetBehavior.from<CoordinatorLayout>(bottomSheet)


        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, slideOffset: Float) {

                Log.d("TAG", "slideOffset " + slideOffset)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                Log.d("TAG", "newState $newState")
            }
        })


        tMapView.setCenterPoint(126.9573662, 37.5048935)
        val tMapPointStart = TMapPoint(37.5048935, 126.9573662) // 중앙대학교
        val tMapPointEnd = TMapPoint(37.5099724, 126.9949061) // 반포한강공원
        findpath(tMapPointStart,tMapPointEnd)
        linearLayoutTmap.addView(tMapView)

        tMapView.setOnClickListenerCallBack(object : TMapView.OnClickListenerCallback {
            override fun onPressUpEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
                mpoint = p2
                Log.d("tag",mpoint.toString())


                val tpoint = TMapPoint( mpoint?.latitude!!.toDouble() , mpoint?.longitude!!.toDouble())
                val tItem = TMapMarkerItem()
                tItem.tMapPoint = tpoint
                tItem.visible = TMapMarkerItem.VISIBLE
                tMapView.addMarkerItem(tItem.id, tItem)
                return true
            }

            override fun onPressEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
                return true
            }
        })

        presenter = MainPresenter()
        presenter.takeView(linearLayoutTmap)
    }



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
                            tMapView.addTMapPath(path)
                        }
                    })
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}