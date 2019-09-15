package com.gitturami.bike.view.main

import android.Manifest
import android.location.Location
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gitturami.bike.R
import com.gitturami.bike.adapter.RecommendAdapter
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.map.BitmapManager
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
    private lateinit var tMapView: TMapView
    private lateinit var tMapGps: TMapGpsManager
    private var mTracking: Boolean = true
    private val bitmapManager: BitmapManager by lazy {
        BitmapManager(applicationContext)
    }

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(applicationContext)
        initTMapView()
        initRecyclerView()
        initBottomSheet()
        initSettingButton()
        checkPermission()
        initSettingButton()
        presenter.takeView(this)
        presenter.registerObserver()
    }

    override fun onLocationChange(location: Location) {
        if(mTracking){
            tMapView.setLocationPoint(location.longitude, location.latitude) // 마커이동
            tMapView.setCenterPoint(location.longitude, location.latitude)  // 중심이동
        }
    }

    private fun checkPermission() {
        if (!checkLocationPermission()) {
            Logger.i(TAG, "need permission")
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQUEST_LOCATION_PERMISSION)
        } else {
            initFloatingButtonAction()
        }
    }

    private fun initRecyclerView() {
        val recommendAdapter = RecommendAdapter(this)
        val recyclerView: RecyclerView = recycler_view
        recyclerView.adapter = recommendAdapter
    }

    private fun initSettingButton() {
        /*settingButton.setOnClickListener {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
        }*/
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

    private fun initFloatingButtonAction(){
        val fabGps: FloatingActionButton = fab_main as FloatingActionButton
        fabGps.setOnClickListener{
            Logger.i(TAG, "set gps to my location.")
        }
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun initBottomSheet(){
        val bottomSheet: LinearLayout = Bottom_Sheet
        val bottomSheetBehavior: BottomSheetBehavior<LinearLayout> = from(bottomSheet)

        bottomSheet.setOnClickListener {
            bottomSheetBehavior.setState(STATE_HIDDEN)
        }

        val startButton: Button = start
        startButton.setOnClickListener {
            // TODO: add selected location in Path.
            showToast(bottomSheetTitle.text as String)
        }
    }

    private fun initTMapView(){
        Logger.i(TAG, "initTMapView()")
        tMapView = TMapView(this)
        tMapGps = TMapGpsManager(this)
        tMapView.setSKTMapApiKey(getString(R.string.apiKey))
        tMapView.setIconVisibility(true)
        tMapView.zoomLevel = 15
        tMapView.mapType = TMapView.MAPTYPE_STANDARD
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        linearLayoutTmap.addView(tMapView)

        tMapGps.minTime = 1000
        tMapGps.minDistance = 5f
        tMapGps.provider = TMapGpsManager.NETWORK_PROVIDER // 인터넷에 연결(실내에서 유용)
        tMapGps.OpenGps()
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
                    Logger.i(TAG, "permission granted")
                    initFloatingButtonAction()
                } else {
                    Logger.i(TAG, "permission denied")
                }
                return
            }
        }
    }

    override fun setMarker(x: Double, y: Double, station: Station) {
        val markerItem = TMapMarkerItem()
        val mapPoint = TMapPoint(x, y)
        val bitmap = when {
                station.shared > 50 -> bitmapManager.markerGreen
                station.shared in 20..50 -> bitmapManager.markerYellow
                else -> bitmapManager.markerRed
            }
        markerItem.icon = bitmap
        markerItem.setPosition(0.5f, 1.0f)
        markerItem.tMapPoint = mapPoint
        markerItem.id = station.stationId
        tMapView.addMarkerItem(station.stationId, markerItem)

        val markerItem2 = object: TMapMarkerItem2() {
            override fun onSingleTapUp(p: PointF?, mapView: TMapView?): Boolean {
                Logger.i(TAG, "onSingleTapUp() : ${station.stationName}")
                return super.onSingleTapUp(p, mapView)
            }
        }
        markerItem2.icon = bitmap
        markerItem2.setPosition(0.5f, 1.0f)
        markerItem2.tMapPoint = mapPoint
        markerItem2.id = station.stationId
        tMapView.addMarkerItem2(station.stationId, markerItem2)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}