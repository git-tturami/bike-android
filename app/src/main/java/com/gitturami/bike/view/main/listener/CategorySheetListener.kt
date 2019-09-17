package com.gitturami.bike.view.main.listener

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CategorySheetListener(private var bottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>,
                            private val presentCallBack: (State) -> Unit)
    : BottomSheetBehavior.BottomSheetCallback() {
    companion object {
        private val TAG = "CategorySheetListener"
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        Logger.i(TAG, "onSlide()")
        when (slideOffset) {
            0F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            0.5F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED)
            1F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        Logger.i(TAG, "onStateChanged()")
        when (newState) {
            BottomSheetBehavior.STATE_COLLAPSED -> {
                presentCallBack(State.SELECT_CATEGORY)
            }
            BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                presentCallBack(State.HALF_WAYPOINT_SHEET)
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                presentCallBack(State.FULL_WAYPOINT_SHEET)
            }
            else -> {

            }
        }
    }
}