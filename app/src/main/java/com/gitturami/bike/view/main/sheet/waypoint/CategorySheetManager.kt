package com.gitturami.bike.view.main.sheet.waypoint

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.sheet.waypoint.listener.CategorySheetListener
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_waypoint_bottom_sheet.*

class CategorySheetManager(activity: MainActivity) {
    companion object {
        private val TAG = "CategorySheetManager"
    }

    private val wayPointSheet: CoordinatorLayout = activity.waypoint_bottom_sheet
    private val sheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private val sheetListener: CategorySheetListener

    init {
        sheetBehavior = BottomSheetBehavior.from(wayPointSheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        wayPointSheet.setOnTouchListener { view, motionEvent -> true }
        sheetListener = CategorySheetListener(sheetBehavior, activity.getPresenter())
        sheetBehavior.bottomSheetCallback = sheetListener

        activity.category_cafe.setOnClickListener{v -> activity.getPresenter().setState(State.SELECT_WAYPOINT) }
        activity.category_leisure.setOnClickListener{v -> activity.getPresenter().setState(State.SELECT_WAYPOINT)}
        activity.category_restaurant.setOnClickListener{ v ->
            activity.getPresenter().setState(State.SELECT_WAYPOINT)
            activity.getPresenter().setRestaurantMarkers()
        }
        activity.category_terrain.setOnClickListener{v -> activity.getPresenter().setState(State.SELECT_WAYPOINT)}
    }

    fun collapseWayPointSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            Logger.i(TAG, "halfWayPointSheet()")
            sheetBehavior.isHideable = false
            sheetBehavior.peekHeight = 400
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun hiddenWayPointSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
            sheetBehavior.isHideable = true
            sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}