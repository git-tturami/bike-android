package com.gitturami.bike.view.main.sheet.waypoint.listener

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CategorySheetListener(private var behavior: BottomSheetBehavior<CoordinatorLayout>,
                            private val presenter: MainContact.Presenter)
    : BottomSheetBehavior.BottomSheetCallback() {
    companion object {
        private val TAG = "CategorySheetListener"
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        Logger.i(TAG, "onSlide()")
        when (slideOffset) {
            0F -> behavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            0.5F -> behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED)
            1F -> behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        Logger.i(TAG, "onStateChanged()")
        Logger.i(TAG, "onStateChanged()")
        when (newState) {
            BottomSheetBehavior.STATE_HIDDEN -> {
                presenter.setState(State.PREPARE)
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {

            }
            BottomSheetBehavior.STATE_HALF_EXPANDED -> {

            }
            BottomSheetBehavior.STATE_EXPANDED -> {

            }
            else -> {

            }
        }
    }
}