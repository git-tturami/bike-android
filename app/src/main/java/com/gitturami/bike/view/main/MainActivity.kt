package com.gitturami.bike.view.main

import android.Manifest
import android.location.Location
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
import com.gitturami.bike.adapter.RecommendAdapter
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.setting.SettingActivity
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

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(applicationContext)
        initTMapView()
        initRecyclerView()
        initBottomSheet()
        if (!checkLocationPermission()) {
            Logger.i(TAG, "need permission")
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQUEST_LOCATION_PERMISSION)
        } else {
            initGpsAction()
        }
        initSettingButton()
        tMapView.setOnClickListenerCallBack(presenter)
        presenter.takeView(this)
        presenter.takeTMapView(tMapView)
      
        initSettingButton()
    }

    override fun onLocationChange(location: Location) {
        if(mTracking){
            tMapView.setLocationPoint(location.longitude, location.latitude) // 마커이동
            tMapView.setCenterPoint(location.longitude, location.latitude)  // 중심이동
        }
    }

    private fun initRecyclerView() {
        val recommendAdapter = RecommendAdapter(this)
        val recyclerView: RecyclerView = recycler_view
        recyclerView.adapter = recommendAdapter
        presenter.takeRecyclerAdapter(recommendAdapter)
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

    private fun initGpsAction(){
        val fabGps: FloatingActionButton = fab_main as FloatingActionButton
        presenter.setGps(tMapGps)
        fabGps.setOnClickListener(View.OnClickListener {
            presenter.setGps(tMapGps)
        })
    }

    override fun showToast(title: String) {
        Toast.makeText(this, "OnClick Item $title", Toast.LENGTH_SHORT).show()
    }

    private fun initBottomSheet(){
        val bottomSheet: LinearLayout = Bottom_Sheet
        val bottomSheetBehavior: BottomSheetBehavior<LinearLayout> = from(bottomSheet)
        presenter.setBottomSheetBehavior(bottomSheetBehavior)
        bottomSheetBehavior.setBottomSheetCallback(presenter as MainPresenter)

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
                    initGpsAction()
                } else {
                    Logger.i(TAG, "permission denied")
                }
                return
            }
        }
    }
}