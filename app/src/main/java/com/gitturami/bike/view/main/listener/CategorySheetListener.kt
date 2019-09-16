package com.gitturami.bike.view.main.listener

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CategorySheetListener(private var bottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>,
                            private val presentCallBack: (State) -> Unit) : BottomSheetBehavior.BottomSheetCallback() {
    private val TAG = "CategorySheetListener"

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        when (slideOffset) {
            0.5F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED)
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_COLLAPSED -> {
                presentCallBack(State.SET_FINISH)
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