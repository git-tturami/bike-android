package com.gitturami.bike.view.main


import android.Manifest
import android.location.Location
import android.os.Build
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Button
import com.gitturami.bike.R
import com.gitturami.bike.SettingActivity
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast


class MainActivity : AppCompatActivity(), MainContact.View, TMapGpsManager.onLocationChangedCallback {

    private lateinit var presenter: MainContact.Presenter
    private lateinit var tMapView: TMapView
    private lateinit var tMapGps: TMapGpsManager
    private lateinit var fabGps: FloatingActionButton
    private var mTracking: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tb = findViewById<View>(R.id.app_toolbar) as Toolbar
        val st = findViewById<TextView>(R.id.startText)
        val et = findViewById<TextView>(R.id.endText)
        val it = findViewById<TextView>(R.id.infoText)

        st.setOnClickListener(View.OnClickListener {
            val toast = Toast.makeText(applicationContext, "start", Toast.LENGTH_LONG)
            toast.show ()
            // TextView 클릭될 시 할 코드작성
        })

        et.setOnClickListener(View.OnClickListener {
            val toast = Toast.makeText(applicationContext, "end", Toast.LENGTH_LONG)
            toast.show ()
            // TextView 클릭될 시 할 코드작성
        })

        it.setOnClickListener(View.OnClickListener {
            val toast = Toast.makeText(applicationContext, "info", Toast.LENGTH_LONG)
            toast.show ()
            // TextView 클릭될 시 할 코드작성
        })

        val linearLayoutTmap = linearLayoutTmap
        tMapView = TMapView(this)

        tMapView.setSKTMapApiKey("3826adf1-c99a-4e69-95c5-3763539465ea")
        linearLayoutTmap.addView(tMapView)
        tMapView.setCenterPoint(126.9573662, 37.5048935)
        setTmapView()

        presenter = MainPresenter()
        presenter.takeView(this)
        presenter.test()

        setFloatingButtonAction()
      
        initSettingButton()
    }

    override fun onLocationChange(location: Location) {
        if(mTracking){
            tMapView.setLocationPoint(location.longitude, location.latitude) // 마커이동
            tMapView.setCenterPoint(location.longitude, location.latitude)  // 중심이동
        }
    }

    fun initSettingButton() {
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

}