package com.gitturami.bike.view.main.map

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.cafe.pojo.SummaryCafe
import com.gitturami.bike.model.common.pojo.DefaultSummaryItem
import com.gitturami.bike.model.leisure.pojo.Leisure
import com.gitturami.bike.model.path.pojo.PathItem
import com.gitturami.bike.model.leisure.pojo.SummaryLeisure
import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.model.restaurant.pojo.SummaryRestaurant
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.SummaryStation
import com.gitturami.bike.view.main.MainActivity
import com.skt.Tmap.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
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

    fun setMarker(x: Double, y: Double, station: SummaryStation, clickListener: TMapMarkerItem2) {
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
                mainView.loadDetailInfoOfStation(station.stationId)
                return super.onSingleTapUp(p, mapView)
            }
        }

        setMarker(ItemType.STATION, x, y, station.stationId, bitmap, markerOverlay)
    }

    fun setMarker(type: ItemType, x: Double, y: Double, summaryItem: DefaultSummaryItem, clickListener: TMapMarkerItem2) {
        var bitmap: Bitmap =
            when (type) {
                ItemType.CAFE -> {
                    bitmapManager.cafeMarker
                }
                ItemType.RESTAURANT -> {
                    bitmapManager.restaurantMarker
                }
                ItemType.LEISURE -> {
                    bitmapManager.leisureMarker
                }
                ItemType.TERRAIN -> {
                    bitmapManager.terrainMarker
                }
                else -> {
                    bitmapManager.redMarker
                }
            }

        setMarker(type, x, y, summaryItem.getID().replace(" ", "").trim(), bitmap, clickListener)
    }

    private fun setMarker(type: ItemType, x: Double, y: Double, id: String, icon: Bitmap, tMapOverlay: TMapMarkerItem2) {
        if (id == "testId") {
            Logger.i(TAG, "path : $x, $y")
        }

        val point = TMapPoint(x, y)
        if (type == ItemType.CAFE) {
            point.mKatecLat = y
            point.mKatecLon = x
            val katec = TMapKatec()

        }

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

    fun markPath(pathItem: PathItem) {

        var distance = 0
        val posList = arrayListOf<TMapPoint>()

        val disposal = CompositeDisposable()
        val job = Observable.fromIterable(pathItem.features)
                .subscribe(
                        {
                            when (it.geometry.type) {
                                "Point" -> {
                                    posList.add(TMapPoint(it.geometry.coordinates.asJsonArray[1].asDouble,
                                            it.geometry.coordinates.asJsonArray[0].asDouble))
                                }
                                "LineString" -> {
                                    Logger.i(TAG, it.properties.toString())
                                    distance += it.properties.get("distance").asInt
                                }
                            }
                        },
                        { t -> },
                        {
                            Logger.i(TAG, "Distance = $distance")
                            drawLine(posList)
                            isFindPath = true
                        }
                )
        disposal.add(job)
    }

    fun drawLine(posList: List<TMapPoint>) {
        val line = TMapPolyLine()
        line.lineColor = Color.BLUE
        line.lineWidth = 2f
        for (pos in posList) {
            line.addLinePoint(pos)
        }
        tMapView.addTMapPolyLine("line", line)
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
        // tMapView.removeTMapPath()
        tMapView.removeAllTMapPolyLine()
    }
}