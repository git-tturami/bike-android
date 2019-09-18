package com.gitturami.bike.view.main.map

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.SummaryStation
import com.gitturami.bike.view.main.MainActivity
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TmapManager(activity: MainActivity): TMapGpsManager.onLocationChangedCallback {
    companion object {
        const val TAG = "TmapManager"
    }
    private var isTracking = true
    private var isFindPath = false
    var isMarked = false
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

    fun setMarker(x: Double, y: Double, station: SummaryStation) {
        if (isMarked) {
            return
        }
        val bitmap = when {
            station.shared > 50 -> bitmapManager.greenMarker
            station.shared in 20..50 -> bitmapManager.yellowMarker
            else -> bitmapManager.redMarker
        }

        val markerOverlay = object: TMapMarkerItem2() {
            override fun onSingleTapUp(p: PointF?, mapView: TMapView?): Boolean {
                Logger.i(TAG, "onSingleTapUp() : ${station.stationId}")
                mainView.setSelectDialogContants(station.stationId)
                return super.onSingleTapUp(p, mapView)
            }
        }

        setMarker(x, y, station.stationId, bitmap, markerOverlay)
    }

    fun setMarker(x: Double, y: Double, restaurant: Restaurant, clickListener: TMapMarkerItem2) {
//        val clickListener = object: TMapMarkerItem2() {
//            override fun onSingleTapUp(p: PointF?, mapView: TMapView?): Boolean {
//                Logger.i(TAG, "onSingleTapUp() : $restaurant")
//                return super.onSingleTapUp(p, mapView)
//            }
//        }
        setMarker(x, y, restaurant.UPSO_SNO, bitmapManager.restaurantMarker, clickListener)

    }

    private fun setMarker(x: Double, y: Double, id: String, icon: Bitmap, tMapOverlay: TMapMarkerItem2) {
        val point = TMapPoint(x, y)
        val markerItem = TMapMarkerItem()
        markerItem.icon = icon
        markerItem.setPosition(0.5f, 1.0f)
        markerItem.tMapPoint = point
        markerItem.id = id
        tMapView.addMarkerItem(id, markerItem)

        val markerOverlay = tMapOverlay
        markerOverlay.icon = icon
        markerOverlay.setPosition(0.5f, 1.0f)
        markerOverlay.tMapPoint = point
        markerOverlay.id = id
        tMapView.addMarkerItem2(id, markerOverlay)
    }

    fun changeMarker(station: Station) {
        // TODO: add marker at selected station
        // tMapView.removeMarkerItem(station.stationId)
    }

    fun findPath(start: Station, end: Station, bottomSheetAction: () -> Unit) {
        isFindPath = true
        val startTMapPoint = TMapPoint(start.stationLatitude.toDouble(), start.stationLongitude.toDouble())
        val endTMapPoint = TMapPoint(end.stationLatitude.toDouble(), end.stationLongitude.toDouble())
        try {
            val data = TMapData()
            data.findPathData(startTMapPoint, endTMapPoint) { path ->
                CoroutineScope(Dispatchers.Main).launch {
                    path.lineWidth = 10f
                    path.lineColor = Color.BLUE
                    tMapView.addTMapPath(path)
                    bottomSheetAction()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideStationMarker() {
        if (!isMarked) {
            return
        }

        isMarked = false
        tMapView.removeAllMarkerItem()
    }

    fun hidePath() {
        if (!isFindPath) {
            return
        }

        isFindPath = false
        tMapView.removeTMapPath()
    }
}