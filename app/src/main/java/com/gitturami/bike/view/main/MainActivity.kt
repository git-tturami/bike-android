package com.gitturami.bike.view.main

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.gitturami.bike.R
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_bottomsheet.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContact.View {
    private lateinit var presenter: MainContact.Presenter
    private lateinit var tMapView:TMapView
    private lateinit var bottomSheet:LinearLayout

    var apiKey = "3826adf1-c99a-4e69-95c5-3763539465ea"
    var mpoint: TMapPoint? = null
    var data = arrayOf("List1","List2","List3","List4","List5","List6","List7","List8","List9","List10","List11","List12")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutTmap=linearLayoutTmap
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey(apiKey)
        linearLayoutTmap.addView(tMapView)
        tMapView.setCenterPoint(126.9573662, 37.5048935)
        presenter = MainPresenter()
        presenter.takeView(this)
        presenter.test()



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


        val listView = findViewById<ListView>(R.id.list)
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listView.adapter = adapter

        val bottomSheetText:TextView = findViewById(R.id.bottomsheet_text)
        bottomSheet = Bottom_Sheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                when (slideOffset) {
//                    -1F -> bottomSheetBehavior.setState(STATE_EXPANDED)
//                    0F -> bottomSheetBehavior.setState(STATE_HALF_EXPANDED)
//                    1F -> bottomSheetBehavior.setState(STATE_COLLAPSED)
//                    else->{
//
//                    }
//                }
                Log.d("TAG", "slideOffset " + slideOffset)

            }


            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_DRAGGING -> bottomSheetText.setText(R.string.DRAGGING)
                    STATE_SETTLING -> bottomSheetText.setText(R.string.SETTLING)
                    STATE_EXPANDED -> bottomSheetText.setText(R.string.EXPANDED)
                    STATE_COLLAPSED -> bottomSheetText.setText(R.string.COLLAPSED)
                    STATE_HIDDEN -> bottomSheetText.setText(R.string.HIDDEN)
                    STATE_HALF_EXPANDED -> bottomSheetText.setText(R.string.HALF_EXPANDED)
                    else -> {
                    }
                }
                Log.d("TAG", "newState $newState")
            }
        })




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