package com.gitturami.bike.view.main.sheet.waypoint.listener

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DetailWayPointSheetListener(
        private val behavior: BottomSheetBehavior<CoordinatorLayout>,
        private val presenter: MainContact.Presenter) : BottomSheetBehavior.BottomSheetCallback() {
    private val TAG = "DetailWayPointSheetListener"

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        Logger.i(TAG, "onSlide()")
        when (slideOffset) {
            0F -> {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN)
            }
            1F -> {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        Logger.i(TAG, "onStateChanged()")
        when (newState) {
            BottomSheetBehavior.STATE_HIDDEN -> {
                presenter.setState(State.SELECT_CATEGORY)
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                presenter.setState(State.FULL_WAYPOINT_SHEET)
            }
            else -> {

            }
        }
    }
}