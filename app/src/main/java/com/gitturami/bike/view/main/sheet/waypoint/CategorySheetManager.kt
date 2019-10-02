package com.gitturami.bike.view.main.sheet.waypoint

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_category_bottom_sheet.*

class CategorySheetManager(activity: MainActivity) {
    companion object {
        private const val TAG = "CategorySheetManager"
    }

    private val wayPointSheet: CoordinatorLayout = activity.category_bottom_sheet
    private val sheetBehavior: BottomSheetBehavior<CoordinatorLayout>

    init {
        sheetBehavior = BottomSheetBehavior.from(wayPointSheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        wayPointSheet.setOnTouchListener { view, motionEvent -> true }
        sheetBehavior.bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }

        activity.category_shopping.setOnClickListener{ v ->
            activity.getPresenter().setState(State.SELECT_WAYPOINT)
            activity.getPresenter().setShoppingMarkers()
        }
        activity.category_leisure.setOnClickListener{v ->
            activity.getPresenter().setState(State.SELECT_WAYPOINT)
            activity.getPresenter().setLeisureMarkers()
        }
        activity.category_restaurant.setOnClickListener{ v ->
            activity.getPresenter().setState(State.SELECT_WAYPOINT)
            activity.getPresenter().setRestaurantMarkers()
        }
        activity.category_terrain.setOnClickListener{v ->
            activity.getPresenter().setState(State.SELECT_WAYPOINT)
            activity.getPresenter().setTerrainMarkers()
        }
    }

    fun collapseWayPointSheet() {
        Logger.i(TAG, "halfWayPointSheet()")
        sheetBehavior.isHideable = false
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun hiddenWayPointSheet() {
        Logger.i(TAG, "hiddenWayPointSheet")
        sheetBehavior.isHideable = true
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}