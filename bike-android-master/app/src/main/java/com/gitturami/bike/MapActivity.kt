package com.gitturami.bike

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.skt.Tmap.*
import java.util.ArrayList

class MapActivity : AppCompatActivity() {
    private var mContext: Context? = null
    private val m_bTrackingMode = true

    private var tmapgps: TMapGpsManager? = null
    private lateinit var tMapView:TMapView


    private val m_tmapPoint = ArrayList<TMapPoint>()
    private val mArrayMarkerID = ArrayList<String>()
    private val m_mapPoint = ArrayList<MapPoint>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val linearLayoutTmap = findViewById(R.id.linearLayoutTmap) as LinearLayout
        tMapView = TMapView(this)
        tmapgps=TMapGpsManager(this)
        tMapView.setSKTMapApiKey("3826adf1-c99a-4e69-95c5-3763539465ea")
        linearLayoutTmap.addView(tMapView)

       // tMapView.setCenterPoint(126.9573662, 37.5048935)

        val tMapPointStart = TMapPoint(37.5048935, 126.9573662) // 중앙대학교
        val tMapPointEnd = TMapPoint(37.5099724, 126.9949061) // 반포한강공원

        tMapView!!.setCompassMode(true)

        /* 현위치 아이콘표시 */
        tMapView!!.setIconVisibility(true)

        /* 줌레벨 */
        tMapView!!.setZoomLevel(15)
        tMapView!!.setMapType(TMapView.MAPTYPE_STANDARD)
        tMapView!!.setLanguage(TMapView.LANGUAGE_KOREAN)

        tmapgps = TMapGpsManager(this@MapActivity)
        tmapgps!!.setMinTime(1000)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf( Manifest.permission.ACCESS_FINE_LOCATION), 1);
        }
        return;

        tmapgps!!.setProvider(tmapgps!!.provider)
        tmapgps!!.OpenGps()
        /*  화면중심을 단말의 현재위치로 이동 */
        tMapView!!.setTrackingMode(true)
        tMapView!!.setSightVisible(true)

        try {
            val data = TMapData()
            data.findPathData(tMapPointStart, tMapPointEnd, object: TMapData.FindPathDataListenerCallback {
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

