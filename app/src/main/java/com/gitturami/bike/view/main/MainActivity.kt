package com.gitturami.bike.view.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.SummaryStation
import com.gitturami.bike.view.main.map.TmapManager
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.gitturami.bike.view.main.sheet.select.SelectLocationSheetManager
import com.gitturami.bike.view.main.sheet.waypoint.DetailWayPointSheetManager
import com.gitturami.bike.view.main.sheet.waypoint.CategorySheetManager
import com.gitturami.bike.view.main.sheet.waypoint.ItemSheetManager
import com.gitturami.bike.view.main.state.State
import com.gitturami.bike.view.setting.SettingActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContact.View {
    companion object {
        private val TAG = "MainActivity"
    }

    private lateinit var presenter: MainContact.Presenter

    private val wayPointSheetManager by lazy {
        CategorySheetManager(this)
    }

    private val detailWayPointSheetManager by lazy {
        DetailWayPointSheetManager(this) { state: State -> presenter.setState(state)}
    }

    private val tMapManager by lazy {
        TmapManager(this)
    }

    private val selectLocationSheetManager by lazy {
        SelectLocationSheetManager(presenter, this)
    }

    private val itemSheetManager by lazy {
        ItemSheetManager(this)
    }

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(applicationContext)

        initSettingButton()
        checkPermission()

        presenter.takeView(this)
        setStationMarkers()
    }

    override fun getPresenter(): MainContact.Presenter = presenter

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

    override fun findPath(start: Station, end: Station) {
        tMapManager.findPath(start, end) {
            presenter.setState(State.SELECT_CATEGORY)
        }
    }

    override fun hidePath() {
        tMapManager.hidePath()
    }

    override fun clearPath() {
        tMapManager.clearPath()
    }

    private fun initFloatingButtonAction() {
        val fabGps: FloatingActionButton = fab_main as FloatingActionButton
        fabGps.setOnClickListener {
            tMapManager.setGpsToCurrentLocation()
        }
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun checkLocationPermission(): Boolean {
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

    override fun setStartSearchView(text: String) {
        Logger.i(TAG, "setStartSearchView : $text")
        startSearchView.text = text
    }

    override fun setFinishSearchView(text: String) {
        Logger.i(TAG, "setFinishSearchView : $text")
        finishSearchView.text = text
    }

    override fun setSelectDialogContants(id: String) {
        presenter.loadDetailInfoOfStation(id)
    }

    override fun setSelectBottomSheet(station: Station) {
        selectLocationSheetManager.initStation(station)
        selectLocationSheetManager.collapseSelectSheet()
    }

    override fun onBackPressed() {
        when (presenter.getState()) {
            State.SET_START -> {
                Toast.makeText(applicationContext, "출발지 초기화", Toast.LENGTH_SHORT).show()
                presenter.setLocation(null)
                presenter.setState(State.PREPARE)
            }
            State.SET_FINISH -> {
                Toast.makeText(applicationContext, "도착지 초기화", Toast.LENGTH_SHORT).show()
                presenter.setLocation(null)
                presenter.setState(State.SET_START)
                wayPointSheetManager.hiddenWayPointSheet()
            }
            State.SELECT_CATEGORY -> {
                Toast.makeText(applicationContext, "초기화", Toast.LENGTH_SHORT).show()
                presenter.setState(State.PREPARE)
                presenter.setStationMarkers()
            }
            State.SELECT_WAYPOINT -> {
                presenter.setState(State.SELECT_CATEGORY)
            }
            State.POST_SELECT_WAYPOINT -> {
                presenter.setState(State.SELECT_WAYPOINT)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun setStationMarkers() {
        if (!tMapManager.isMarked) {
            presenter.setStationMarkers()
        }
    }

    override fun setRestaurantMarkers(x: Double, y: Double, restaurant: Restaurant) {
        presenter.setRestaurantMarkers()
    }

    override fun setMarker(x: Double, y: Double, station: SummaryStation) {
        tMapManager.setMarker(x, y, station)
    }

    override fun setMarker(x: Double, y: Double, restaurant: Restaurant) {
        tMapManager.setMarker(
                x = x,
                y = y,
                restaurant = restaurant,
                clickListener = object: TMapMarkerItem2() {
                    override fun onSingleTapUp(p: PointF?, mapView: TMapView?): Boolean {
                        presenter.setState(State.POST_SELECT_WAYPOINT)
                        itemSheetManager.setItem(restaurant)
                        return super.onSingleTapUp(p, mapView)
                    }
        })
    }

    override fun onCompleteMarking() {
        Logger.i(TAG, "onCompleteMarking")
        tMapManager.isMarked = true
    }

    override fun changeMarker(station: Station) {
        tMapManager.changeMarker(station)
    }

    override fun hideAllMarkers() {
        wayPointSheetManager.hiddenWayPointSheet()
        detailWayPointSheetManager.collapseWayPointSheet()
        tMapManager.hideStationMarker()
    }

    override fun hideCategorySheet() {
        wayPointSheetManager.hiddenWayPointSheet()
    }

    override fun collapseCategorySheet() {
        wayPointSheetManager.collapseWayPointSheet()
    }

    override fun hideWayPointSheet() {
        detailWayPointSheetManager.hiddenWayPointSheet()
    }

    override fun collapseWayPointSheet() {
        detailWayPointSheetManager.collapseWayPointSheet()
    }

    override fun halfWayPointSheet() {
        detailWayPointSheetManager.halfWayPointSheet()
    }

    override fun expandWayPointSheet() {
        detailWayPointSheetManager.expandWayPointSheet()
    }

    override fun addWayPointItem(item: DefaultItem) {
        detailWayPointSheetManager.addItem(item)
    }

    override fun collapseItemSheet() {
        itemSheetManager.collapseSheet()
    }

    override fun hideItemSheet() {
        itemSheetManager.hideSheet()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}