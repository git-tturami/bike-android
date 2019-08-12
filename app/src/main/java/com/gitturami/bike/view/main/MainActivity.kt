package com.gitturami.bike.view.main

import android.Manifest
import android.location.Location
import android.os.Build
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.core.app.ActivityCompat
import com.gitturami.bike.R
import com.gitturami.bike.adapter.TitleAdapter
import com.gitturami.bike.SettingActivity
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_bottomsheet.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContact.View, TMapGpsManager.onLocationChangedCallback {

    private lateinit var presenter: MainContact.Presenter
    private lateinit var titleAdapter: TitleAdapter
    private lateinit var tMapView:TMapView
    private lateinit var bottomSheet:LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var tMapGps: TMapGpsManager
    private lateinit var fabGps: FloatingActionButton
    private var mTracking: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleAdapter = TitleAdapter(this)
        recyclerView = recycler_view
        recyclerView.adapter = titleAdapter

        setTmapView()
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

        //make the bottomsheet
        bottomSheet = Bottom_Sheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        presenter.setBottomSheetBehavior(bottomSheetBehavior)
        bottomSheetBehavior.setBottomSheetCallback(presenter as MainPresenter)

        //출발지 button
        val btn_click_me = start
        btn_click_me.setOnClickListener {
            bottomSheetBehavior.setState(STATE_HIDDEN)
        }

        setFloatingButtonAction()
        initSettingButton()
    }

    override fun onLocationChange(location: Location) {
        if(mTracking){
            tMapView.setLocationPoint(location.longitude, location.latitude) // 마커이동
            tMapView.setCenterPoint(location.longitude, location.latitude)  // 중심이동
        }
    }

    private fun initSettingButton() {
        settingButton.setOnClickListener {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun findPath(start:TMapPoint, end:TMapPoint) {
        try {
            val data = TMapData()
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

    private fun setFloatingButtonAction(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_NETWORK_STATE), 1);
        }
        fabGps = findViewById(R.id.fab_main) as FloatingActionButton
        presenter.setGps(tMapGps)

        fabGps.setOnClickListener(View.OnClickListener {
            presenter.setGps(tMapGps)
        })
    }

    override fun showToast(title: String) {
        testForClick.also {
            it.setText(title)
        }
    }

    private fun setTmapView(){
        val linearLayoutTmap = linearLayoutTmap
        tMapView = TMapView(this)
        tMapGps = TMapGpsManager(this)
        tMapView.setSKTMapApiKey(this.getString(R.string.apiKey))
        linearLayoutTmap.addView(tMapView)
        tMapView.setIconVisibility(true)
        tMapView.setZoomLevel(15)
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD)
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
    }

    override fun changeState(strings: String) {
        bottomSheetText.also {
            it.setText(strings)
        }
    }
}