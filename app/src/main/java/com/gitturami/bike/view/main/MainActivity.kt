package com.gitturami.bike.view.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.R
import com.gitturami.bike.adapter.TitleAdapter
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_bottomsheet.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContact.View {
    private lateinit var presenter: MainContact.Presenter
    private lateinit var titleAdapter: TitleAdapter
    private lateinit var tMapView:TMapView
    private lateinit var bottomSheet:LinearLayout


    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayoutTmap=linearLayoutTmap

        titleAdapter = TitleAdapter(this)
        recyclerView = recycler_view
        recyclerView.adapter = titleAdapter

        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey(apiKey)
        linearLayoutTmap.addView(tMapView)
        tMapView.setCenterPoint(126.9573662, 37.5048935)
        presenter = MainPresenter()
                .apply {
            titleAdapterModel = titleAdapter
            titleAdapterView = titleAdapter
            tmapView = tMapView


        }

        tMapView.setOnClickListenerCallBack(presenter)
        presenter.loadItems(this, false)
        presenter.takeView(this)
        presenter.test()


        //bottomsheet
        val bottomSheetText:TextView = bottomSheetText
        bottomSheet = Bottom_Sheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        presenter.setBottomSheetBehavior(bottomSheetBehavior)
        bottomSheetBehavior.setBottomSheetCallback(presenter as MainPresenter)

        //출발지 button
        val btn_click_me = start
        btn_click_me.setOnClickListener {

            bottomSheetBehavior.setState(STATE_HIDDEN)
        }
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

    override fun showToast(title: String) {
//        fortesting.also->{
//        (title)
//    }

}