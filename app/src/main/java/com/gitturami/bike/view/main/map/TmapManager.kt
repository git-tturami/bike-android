package com.gitturami.bike.view.main.map

import android.graphics.PointF
import android.location.Location
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.presenter.MainContact
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_main.*

class TmapManager(activity: MainActivity): TMapGpsManager.onLocationChangedCallback {
    companion object {
        const val TAG = "TmapManager"
    }
    private var isTracking = true
    private val tMapView = TMapView(activity)
    private val tMapGps = TMapGpsManager(activity)
    private val bitmapManager: BitmapManager by lazy {
        BitmapManager(activity.applicationContext)
    }
    private val mainView: MainActivity = activity

    init {
        initTmapView(activity)
        initTmapGps()
    }

    private fun initTmapView(activity: MainActivity) {
        tMapView.setSKTMapApiKey(activity.getString(R.string.apiKey))
        tMapView.setIconVisibility(true)
        tMapView.zoomLevel = 15
        tMapView.mapType = TMapView.MAPTYPE_STANDARD
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        activity.linearLayoutTmap.addView(tMapView)
    }

    private fun initTmapGps() {
        tMapGps.minTime = 1000
        tMapGps.minDistance = 5f
        tMapGps.provider = TMapGpsManager.NETWORK_PROVIDER
        tMapGps.OpenGps()
    }

    override fun onLocationChange(location: Location) {
        if (!isTracking) {
            return
        }
        tMapView.setLocationPoint(location.longitude, location.latitude)
        tMapView.setCenterPoint(location.longitude, location.latitude)
    }

    fun addTMapPath(path: TMapPolyLine) {
        tMapView.addTMapPath(path)
    }

    fun clearPath() {
        tMapView.removeTMapPath()
    }

    fun setGpsToCurrentLocation() {
        initTmapGps()
    }

    fun setMarker(x: Double, y: Double, station: Station) {
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
                Logger.i(TAG, "onSingleTapUp() : $station")
                mainView.setSelectDialogContants(station)
                return super.onSingleTapUp(p, mapView)
            }
        }
        markerItem2.icon = bitmap
        markerItem2.setPosition(0.5f, 1.0f)
        markerItem2.tMapPoint = mapPoint
        markerItem2.id = station.stationId
        tMapView.addMarkerItem2(station.stationId, markerItem2)
    }
}