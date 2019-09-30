package com.gitturami.bike.view.main.sheet.camera

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_fab.*
import kotlinx.android.synthetic.main.layout_fab.view.*

class CameraSheetManager(activity: MainActivity) {
    companion object {
        private const val TAG = "CameraSheetManager"
    }

    private val sheet = activity.layout_fab_camera
    private val fab = activity.layout_fab_camera.fab_camera
    private val behavior: BottomSheetBehavior<CoordinatorLayout>

    init {
        behavior = BottomSheetBehavior.from(sheet)
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        fab.setOnClickListener() {
            v -> Logger.i(TAG, "touch camera button")
        }
    }

    fun hideSheet() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun openSheet() {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}