package com.gitturami.bike.view.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.R
import com.gitturami.bike.adapter.RecommendAdapter
import com.gitturami.bike.adapter.contact.RecommendAdapterContact
import com.gitturami.bike.data.RecyclerItem
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.listener.TMapOnClickListener
import com.gitturami.bike.view.main.map.BitmapManager
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.gitturami.bike.view.main.state.State
import com.gitturami.bike.view.setting.SettingActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class MainActivity : AppCompatActivity(), MainContact.View, TMapGpsManager.onLocationChangedCallback {
    companion object {
        private val TAG = "MainActivity"
    }

    private lateinit var presenter: MainContact.Presenter
    override lateinit var tMapView: TMapView
    private lateinit var tMapGps: TMapGpsManager
    private var mTracking: Boolean = true
    private val bitmapManager: BitmapManager by lazy {
        BitmapManager(applicationContext)
    }

    private lateinit var bottomSheetDialog: BottomSheetDialog

    lateinit var recommendAdapterModel: RecommendAdapterContact.Model
    var recommendAdapterView: RecommendAdapterContact.View? = null
        set(value) {
            field = value
            field?.onClickFunc = { position -> onClickListener(position)}
        }

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(applicationContext)

        bottomSheetDialog = BottomSheetDialog(presenter)
        initRecyclerView()
        initSettingButton()
        initTMapView()
        checkPermission()

        // tMapView.setOnClickListenerCallBack(TMapOnClickListener(this))
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

    private fun initFloatingButtonAction() {
        val fabGps: FloatingActionButton = fab_main as FloatingActionButton
        setGps(tMapGps)
        fabGps.setOnClickListener {
            setGps(tMapGps)
        }
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun setGps(tMapGps: TMapGpsManager) {
        tMapGps.minTime = 1000
        tMapGps.minDistance = 5f
        tMapGps.provider = TMapGpsManager.NETWORK_PROVIDER // 인터넷에 연결(실내에서 유용)
        tMapGps.OpenGps()
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
                Logger.i(TAG, "onSingleTapUp() : ${station}")
                bottomSheetDialog.station = station
                bottomSheetDialog.show(supportFragmentManager, "bs")
                return super.onSingleTapUp(p, mapView)
            }
        }
        markerItem2.icon = bitmap
        markerItem2.setPosition(0.5f, 1.0f)
        markerItem2.tMapPoint = mapPoint
        markerItem2.id = station.stationId
        tMapView.addMarkerItem2(station.stationId, markerItem2)
    }

    override fun setStartSearchView(text: String) {
        Logger.i(TAG, "setStartSearchView : $text")
        startSearchView.text = text
        presenter.setState(State.SET_START)
    }

    override fun setFinishSearchView(text: String) {
        Logger.i(TAG, "setFinishSearchView : $text")
        finishSearchView.text = text
        presenter.setState(State.SET_FINISH)
    }

    override fun onBackPressed() {
        when (presenter.getState()) {
            State.SET_START -> {
                Toast.makeText(applicationContext, "출발지 초기화", Toast.LENGTH_SHORT).show()
                presenter.setState(State.PREPARE)
                startSearchView.text = ""
            }
            State.SET_FINISH -> {
                Toast.makeText(applicationContext, "도착지 초기화", Toast.LENGTH_SHORT).show()
                presenter.setState(State.SET_START)
                finishSearchView.text = ""
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    // TODO: When context is defined, this function is called at Model.
    fun loadItems(locationList: ArrayList<RecyclerItem>) {
        recommendAdapterModel.addItems(locationList)
        recommendAdapterView?.notifyAdapter()
    }

    // TODO: add selected location of item in Path.
    private fun onClickListener(position: Int) {
        Logger.i(BottomSheetDialog.TAG, recommendAdapterModel.getItem(position).title)
    }

    private fun initRecyclerView() {
        /*val recommendAdapter = RecommendAdapter(this)
        val recyclerView: RecyclerView = recycler_view
        recyclerView.adapter = recommendAdapter
        recommendAdapterModel = recommendAdapter
        recommendAdapterView = recommendAdapter*/
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}