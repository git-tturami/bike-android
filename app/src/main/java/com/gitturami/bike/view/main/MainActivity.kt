package com.gitturami.bike.view.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.model.common.pojo.DefaultSummaryItem
import com.gitturami.bike.model.path.pojo.PathItem
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.utils.ScreenShotUtil
import com.gitturami.bike.view.main.map.ItemType
import com.gitturami.bike.view.main.map.TmapManager
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.gitturami.bike.view.main.sheet.select.StationSheet
import com.gitturami.bike.view.main.screenshot.ScreenshotDialog
import com.gitturami.bike.view.main.sheet.waypoint.CategorySheetManager
import com.gitturami.bike.view.main.sheet.waypoint.DetailWayPointSheetManager
import com.gitturami.bike.view.main.sheet.waypoint.ItemSheetManager
import com.gitturami.bike.view.main.state.State
import com.gitturami.bike.view.setting.SettingActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loading_img.*

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

    private val stationSheetManager by lazy {
        StationSheet(presenter, this)
    }

    private val itemSheetManager by lazy {
        ItemSheetManager(this)
    }

    private val loadingDialog: AppCompatDialog by lazy {
        val dialog = AppCompatDialog(this)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.layout_loading_img)
        Glide.with(this)
                .asGif()
                .load(R.raw.loading2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(dialog.img_view_loading)
        dialog
    }

    private val screenshotDialog by lazy {
        ScreenshotDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(applicationContext)

        initSettingButton()

        presenter.takeView(this)
        setStationMarkers()

        itemSheetManager.setButtonClickListener(View.OnClickListener {
            presenter.setWayPointAndFindPath(itemSheetManager.selectedItem!!)
        })
    }

    override fun getPresenter(): MainContact.Presenter = presenter

    private fun initSettingButton() {
        settingButton.setOnClickListener {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun markPath(pathItem: PathItem): Int {
        return tMapManager.markPath(pathItem)
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

    override fun setStartSearchView(text: String) {
        Logger.i(TAG, "setStartSearchView : $text")
        startSearchView.text = text
    }

    override fun setFinishSearchView(text: String) {
        Logger.i(TAG, "setFinishSearchView : $text")
        finishSearchView.text = text
    }

    override fun setSelectBottomSheet(station: Station) {
        stationSheetManager.initStation(station)
        stationSheetManager.collapseSelectSheet()
    }

    override fun hideStationSheet() {
        stationSheetManager.hiddenSelectSheet()
    }

    override fun setItemBottomSheet(item: DefaultItem) {
        itemSheetManager.setItem(item)
        itemSheetManager.collapseSheet()
    }

    override fun onBackPressed() {
        when (presenter.getState()) {
            State.SHOW_START -> {
                presenter.setState(State.PREPARE)
            }
            State.SET_START -> {
                Toast.makeText(applicationContext, "출발지 초기화", Toast.LENGTH_SHORT).show()
                presenter.setLocation(null)
                tMapManager.removeDepartureMarker()
                presenter.setState(State.PREPARE)
            }
            State.SHOW_FINISH -> {
                presenter.setState(State.SET_START)
            }
            State.SET_FINISH -> {
                Toast.makeText(applicationContext, "도착지 초기화", Toast.LENGTH_SHORT).show()
                presenter.setLocation(null)
                presenter.setState(State.SET_START)
                tMapManager.removeArrivalMarker()
                wayPointSheetManager.hiddenWayPointSheet()
            }
            State.SELECT_CATEGORY -> {
                Toast.makeText(applicationContext, "초기화", Toast.LENGTH_SHORT).show()
                presenter.setLocation(null)
                tMapManager.removeArrivalMarker()
                tMapManager.removeDepartureMarker()
                tMapManager.removeCategoryMarkers()
                detailWayPointSheetManager.clearItem()
                presenter.setState(State.PREPARE)
                presenter.setStationMarkers()
            }
            State.SELECT_WAYPOINT -> {
                presenter.setState(State.SELECT_CATEGORY)
            }
            State.POST_SELECT_WAYPOINT -> {
                presenter.setState(State.SELECT_WAYPOINT)
            }
            State.SHOW_SCREENSHOT -> {
                // TODO: back button
                hideScreenshotDialog()
                presenter.setState(State.POST_SELECT_WAYPOINT)
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

    override fun setRestaurantMarkers() {
        presenter.setRestaurantMarkers()
    }
    override fun setCafeMarkers() {
        presenter.setCafeMarkers()
    }
    override fun setLeisureMarkers() {
        presenter.setLeisureMarkers()
    }
    override fun setTerrainMarkers() {
        presenter.setTerrainMarkers()
    }

    override fun setMarkerColorByShared(id: String, shared: Int) {
        tMapManager.setMarkerByShared(id, shared)
    }

    override fun setMarker(type: ItemType, item: DefaultSummaryItem, onClick: () -> Unit) {
        tMapManager.setMarker(
                x = item.getLatitude().toDouble(),
                y = item.getLongitude().toDouble(),
                id = item.getID(),
                type = type,
                onClick = onClick
        )
    }

    override fun setItem(item: DefaultItem) {
        itemSheetManager.setItem(item)
    }

    override fun onCompleteMarking() {
        Logger.i(TAG, "onCompleteMarking")
        tMapManager.isMarked = true
    }

    override fun setWayPointMarker(item: DefaultItem) {
        tMapManager.setMarker(
                x = item.getLatitude().toDouble(),
                y = item.getLongitude().toDouble(),
                id = TmapManager.Constants.LAYOVER.name,
                type = ItemType.LAYOVER)
    }

    override fun setStartMarker(station: Station) {
        tMapManager.setMarker(
                x = station.stationLatitude.toDouble(),
                y = station.stationLongitude.toDouble(),
                id = TmapManager.Constants.DEPARTURE.name,
                type = ItemType.DEPARTURE)
    }

    override fun setFinishMarker(station: Station) {
        tMapManager.setMarker(
                x = station.stationLatitude.toDouble(),
                y = station.stationLongitude.toDouble(),
                id = TmapManager.Constants.ARRIVAL.name,
                type = ItemType.ARRIVAL)
    }

    override fun hideStartMarker() {
        tMapManager.removeMarkerByType(ItemType.DEPARTURE)
    }

    override fun hideFinishMarker() {
        tMapManager.removeMarkerByType(ItemType.ARRIVAL)
    }

    override fun clearWayPointRecyclerItemList() {
        detailWayPointSheetManager.clearItem()
    }

    override fun hideAllMarkers() {
        tMapManager.hideStationMarker()
    }

    override fun hideCategoryMarkers() {
        Logger.i(TAG, "hideCategoryMarkers")
        tMapManager.removeCategoryMarkers()
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

    override fun addWayPointItem(item: DefaultSummaryItem) {
        detailWayPointSheetManager.addItem(item)
    }

    override fun collapseItemSheet() {
        itemSheetManager.collapseSheet()
    }

    override fun hideItemSheet() {
        itemSheetManager.hideSheet()
    }

    override fun showScreenshotDialog() {
        // TODO: implement distance and screenshot
        val screenShot = tMapManager.screenShot()
        ScreenShotUtil.saveBitmapToGallay(screenShot)
        val startStationName = presenter.getStartStationName()!!
        val finishStationName = presenter.getEndStationName()!!
        val wayPointName = presenter.getWayPointName()!!
        val distance = presenter.getDistance().toString()

        screenshotDialog.show(screenShot, startStationName, wayPointName, finishStationName, distance)
    }

    override fun hideScreenshotDialog() {
        screenshotDialog.hide()
    }

    override fun startLoading() {
        Logger.i(TAG, "startLoading")
        loadingDialog.show()
    }

    override fun endLoading() {
        loadingDialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}