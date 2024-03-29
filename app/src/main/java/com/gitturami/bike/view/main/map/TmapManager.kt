package com.gitturami.bike.view.main.map

import android.graphics.Bitmap
import android.graphics.PointF
import android.location.Location
import androidx.core.view.drawToBitmap
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.path.pojo.PathItem
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.map.listener.TMapListener
import com.skt.Tmap.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class TmapManager(activity: MainActivity) {
    companion object {
        const val TAG = "TmapManager"
    }
    private var isFindPath = false
    var isMarked = false
    private val tMapView = TMapView(activity)
    private val tMapGps = TMapGpsManager(activity)
    private val bitmapManager: BitmapManager by lazy {
        BitmapManager(activity.applicationContext)
    }
    private val bitmapMap = hashMapOf(
            ItemType.CAFE to bitmapManager.cafeMarker,
            ItemType.LEISURE to bitmapManager.leisureMarker,
            ItemType.TERRAIN to bitmapManager.terrainMarker,
            ItemType.ARRIVAL to bitmapManager.arrivalmarker,
            ItemType.DEPARTURE to bitmapManager.departureMarker,
            ItemType.LAYOVER to bitmapManager.layoverMarker,
            ItemType.RESTAURANT to bitmapManager.restaurantMarker,
            ItemType.STATION_EMPTY to bitmapManager.greenMarker,
            ItemType.STATION_SUITE to bitmapManager.yellowMarker,
            ItemType.STATION_FULL to bitmapManager.redMarker,
            ItemType.HOTEL to bitmapManager.hotelMarker,
            ItemType.FOOD to bitmapManager.foodMarker,
            ItemType.SHOPPING to bitmapManager.shoppingMarker,
            ItemType.TERRAIN to bitmapManager.parkMarker
    )

    enum class Constants {
        DEPARTURE,
        ARRIVAL,
        LAYOVER
    }

    private val idList = arrayListOf<String>()

    init {
        initTmapView(activity)
        tMapView.setOnClickListenerCallBack(TMapListener(activity))
        initTmapGps()
    }

    private fun initTmapView(activity: MainActivity) {
        tMapView.setSKTMapApiKey(activity.getString(R.string.apiKey))
        tMapView.setIconVisibility(true)
        tMapView.zoomLevel = 15
        tMapView.mapType = TMapView.MAPTYPE_STANDARD
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        activity.linearLayoutTmap.addView(tMapView)
        tMapGps.setLocationCallback()
    }

    private fun initTmapGps() {
        Logger.i(TAG, "initTmapGps()")
        tMapGps.minTime = 1000
        tMapGps.minDistance = 5f
        tMapGps.provider = TMapGpsManager.NETWORK_PROVIDER
        tMapGps.OpenGps()
    }

    fun setLocation(location: Location) {
        tMapView.setLocationPoint(location.longitude, location.latitude)
        tMapView.setCenterPoint(location.longitude, location.latitude)
    }

    fun clearPath() {
        tMapView.removeTMapPath()
    }

    fun setGpsToCurrentLocation() {
        initTmapGps()
    }

    fun setMarker(x: Double, y: Double, id: String, type: ItemType) {
        setMarker(x = x,
                y = y,
                id = id,
                type = type,
                onClick = {})
    }

    fun setMarker(x: Double, y: Double, id: String, type: ItemType, onClick: () -> Unit) {
        val markerItem = TMapMarkerItem()
        markerItem.icon = bitmapMap.getValue(type)
        markerItem.setPosition(0.5f, 1.0f)
        markerItem.tMapPoint = TMapPoint(x, y)
        markerItem.id = id
        tMapView.addMarkerItem(id, markerItem)

        val markerOverlay = object: TMapMarkerItem2() {
            override fun onSingleTapUp(p: PointF?, mapView: TMapView?): Boolean {
                onClick.invoke()
                return super.onSingleTapUp(p, mapView)
            }
        }
        markerOverlay.icon = bitmapMap.getValue(type)
        markerOverlay.setPosition(0.5f, 1.0f)
        markerOverlay.tMapPoint = markerItem.tMapPoint
        markerOverlay.id = id
        tMapView.addMarkerItem2(id, markerOverlay)

        if (!isStation(type)) {
            categoryIdList.add(id)
        }
    }

    private fun isStation(item: ItemType) = item == ItemType.STATION_EMPTY ||
            item == ItemType.STATION_SUITE ||
            item == ItemType.STATION_FULL ||
            item == ItemType.ARRIVAL ||
            item == ItemType.DEPARTURE

    fun setMarkerByShared(id: String, shared: Int) {
        val bitmap = when {
            shared > 50 -> bitmapManager.greenMarker
            shared in 20..50 -> bitmapManager.yellowMarker
            else -> bitmapManager.redMarker
        }
        tMapView.getMarkerItemFromID(id).icon = bitmap
        idList.add(id)
    }

    fun markPath(pathItem: PathItem): Int {
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
                                    distance += it.properties.get("distance").asInt
                                }
                            }
                        },
                        { t -> Logger.e(TAG, "onError() :{ $t") },
                        {
                            Logger.i(TAG, "Distance = $distance")
                            drawLine(posList)
                            isFindPath = true
                        }
                )
        disposal.add(job)
        return distance
    }

    fun drawLine(posList: List<TMapPoint>) {
        val line = TMapPolyLine()
        line.lineColor = 0x4141E0
        line.outLineColor = 0x4141E0
        line.lineWidth = 1.5f
        for (pos in posList) {
            line.addLinePoint(pos)
        }
        tMapView.addTMapPolyLine("line", line)
    }

    fun hideStationMarker() {
        for (id in idList) {
            tMapView.removeMarkerItem(id)
            tMapView.removeMarkerItem2(id)
        }
        idList.clear()
    }

    fun hidePath() {
        if (!isFindPath) {
            return
        }
        isFindPath = false
        tMapView.removeAllTMapPolyLine()
    }

    fun removeDepartureMarker() {
        tMapView.removeMarkerItem(Constants.DEPARTURE.name)
        tMapView.removeMarkerItem2(Constants.DEPARTURE.name)
    }

    fun removeArrivalMarker() {
        tMapView.removeMarkerItem(Constants.ARRIVAL.name)
        tMapView.removeMarkerItem2(Constants.ARRIVAL.name)
    }

    private val categoryIdList = arrayListOf<String>()

    fun addCategoryId(id: String) {
        categoryIdList.add(id)
    }

    fun removeCategoryMarkers() {
        for (id in categoryIdList) {
            tMapView.removeMarkerItem(id)
            tMapView.removeMarkerItem2(id)
        }
        categoryIdList.clear()
    }

    fun removeAllMarkers() {
        tMapView.removeAllMarkerItem()
    }

    fun removeMarkerByType(type: ItemType) {
        tMapView.removeMarkerItem(type.name)
        tMapView.removeMarkerItem2(type.name)
    }

    fun screenShot(): Bitmap {
        return tMapView.drawToBitmap(Bitmap.Config.ARGB_8888)
    }
}