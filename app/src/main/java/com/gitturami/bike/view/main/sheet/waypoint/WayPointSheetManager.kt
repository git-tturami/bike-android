package com.gitturami.bike.view.main.sheet.waypoint

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_waypoint_bottom_sheet.*

class WayPointSheetManager(activity: MainActivity) {
    companion object {
        private val TAG = "WayPointSheetManager"
    }

    private val wayPointSheet: CoordinatorLayout = activity.waypoint_bottom_sheet
    private val sheetBehavior: BottomSheetBehavior<CoordinatorLayout>

    init {
        sheetBehavior = BottomSheetBehavior.from(wayPointSheet)
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
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun checkHeightAndSet() {
        if (sheetBehavior.peekHeight != 400) {
            sheetBehavior.peekHeight = 400
        }
    }
}