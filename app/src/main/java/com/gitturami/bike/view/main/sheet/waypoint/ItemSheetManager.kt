package com.gitturami.bike.view.main.sheet.waypoint

import androidx.constraintlayout.widget.ConstraintLayout
import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_item_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_item_bottom_sheet.view.*

class ItemSheetManager(activity: MainActivity) {
    companion object {
        private const val TAG ="ItemSheetManager"
    }

    private val sheet: ConstraintLayout = activity.item_bottom_sheet
    private val behavior: BottomSheetBehavior<ConstraintLayout>

    init {
        behavior = BottomSheetBehavior.from(sheet)
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        sheet.setOnTouchListener { view, motionEvent -> true }
        // TODO : setup callback
    }

    fun hideSheet() {
        if (behavior.state != BottomSheetBehavior.STATE_HIDDEN) {
            behavior.isHideable = true
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    fun collapseSheet() {
        if (behavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            behavior.isHideable = false
            behavior.peekHeight = 400
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun setItem(item: DefaultItem) {
        sheet.item_title.text = item.getName()
        sheet.item_addr.text = item.getAddress()
    }
}