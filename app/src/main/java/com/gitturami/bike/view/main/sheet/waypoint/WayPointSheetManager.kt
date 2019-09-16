package com.gitturami.bike.view.main.sheet.waypoint

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.adapter.WayPointAdapter
import com.gitturami.bike.adapter.contact.WayPointAdapterContact
import com.gitturami.bike.data.RecyclerItem
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.listener.CategorySheetListener
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_waypoint_bottom_sheet.*

class WayPointSheetManager(activity: MainActivity, listener: (State) -> Unit) {
    companion object {
        private val TAG = "WayPointSheetManager"
    }

    private val wayPointSheet: CoordinatorLayout = activity.waypoint_bottom_sheet
    private val sheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private val wayPointModel: WayPointAdapterContact.Model
    private val wayPointView: WayPointAdapterContact.View
    private val categorySheetListener:CategorySheetListener

    init {
        sheetBehavior = BottomSheetBehavior.from(wayPointSheet)
        categorySheetListener = CategorySheetListener(sheetBehavior, listener)
        sheetBehavior.bottomSheetCallback = categorySheetListener
        sheetBehavior.isHideable = false
        val wayPointAdapter = WayPointAdapter(activity)

        wayPointModel = wayPointAdapter
        wayPointView = wayPointAdapter
        wayPointView.onClickFunc = { position -> onClickListener(position) }
        wayPointSheet.setOnTouchListener { view, motionEvent -> true }

        activity.category_cafe.setOnClickListener{v -> activity.hideStationMarker()}
        activity.category_leisure.setOnClickListener{v -> activity.hideStationMarker()}
        activity.category_restaurant.setOnClickListener{v -> activity.hideStationMarker()}
        activity.category_terrain.setOnClickListener{v -> activity.hideStationMarker()}

    }

    fun collapseWayPointSheet() {
        Logger.i(TAG, "collapseWayPointSheet()")
        checkHeightAndSet()
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun hiddenWayPointSheet() {
        sheetBehavior.isHideable = true
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun checkHeightAndSet() {
        if (sheetBehavior.peekHeight != 400) {
            sheetBehavior.peekHeight = 400
        }
    }

    // TODO: When context is defined, this function is called at Model.
    private fun loadItems(locationList: List<RecyclerItem>) {
        wayPointModel.addItems(locationList)
        wayPointView.notifyAdapter()
    }

    // TODO: add selected location of item in Path.
    private fun onClickListener(position: Int) {
        Logger.i(TAG, wayPointModel.getItem(position).title)
    }
}