package com.gitturami.bike.view.main

import android.Manifest
import android.location.Location
import android.os.Build
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    companion object {
        private val TAG = "MainActivity"
    }

    private lateinit var presenter: MainContact.Presenter
    private lateinit var titleAdapter: TitleAdapter
    private lateinit var tMapView:TMapView
    private lateinit var bottomSheet:LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var tMapGps: TMapGpsManager
    private lateinit var fabGps: FloatingActionButton
    private var mTracking: Boolean = true

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleAdapter = TitleAdapter(this)
        recyclerView = recycler_view
        recyclerView.adapter = titleAdapter

        setTmapView()
        presenter = MainPresenter(this).apply {
            titleAdapterModel = titleAdapter
            titleAdapterView = titleAdapter
            tmapView = tMapView
        }

        tMapView.setOnClickListenerCallBack(presenter)
        presenter.loadItems(this, false)
        presenter.takeView(this)
        presenter.test()

        //make the bottomsheet
        setBottomsheet()
        if (!checkLocationPermission()) {
            Log.i(TAG, "need permission")
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQUEST_LOCATION_PERMISSION)
        } else {
            setFloatingButtonAction()
        }
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
        fabGps = findViewById(R.id.fab_main)
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

    private fun setBottomsheet(){
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

    private fun setTmapView(){
        val linearLayoutTmap = linearLayoutTmap
        tMapView = TMapView(this)
        tMapGps = TMapGpsManager(this)
        tMapView.setSKTMapApiKey(this.getString(R.string.apiKey))
        linearLayoutTmap.addView(tMapView)
        tMapView.setIconVisibility(true)
        tMapView.zoomLevel = 15
        tMapView.mapType = TMapView.MAPTYPE_STANDARD
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
    }

    override fun changeState(strings: String) {
        bottomSheetText.also {
            it.text = strings
        }
    }

    fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i(TAG, "permission granted")
                    setFloatingButtonAction()
                } else {
                    Log.i(TAG, "permission denied")
                }
                return
            }
        }
    }
}