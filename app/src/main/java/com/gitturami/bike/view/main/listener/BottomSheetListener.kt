package com.gitturami.bike.view.main.listener

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetListener(private var bottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>) : BottomSheetBehavior.BottomSheetCallback() {
    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        when (slideOffset) {
            0F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            1F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            else -> {}
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        // TODO: not implemented
    }
}