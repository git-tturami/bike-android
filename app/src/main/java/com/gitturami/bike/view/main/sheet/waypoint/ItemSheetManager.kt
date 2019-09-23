package com.gitturami.bike.view.main.sheet.waypoint

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.gitturami.bike.logger.Logger
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
    private val button = activity.item_select_but
    var selectedItem: DefaultItem? = null
    var lat = 0.0
    var lon = 0.0

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
        lat = item.getLatitude().toDouble()
        lon = item.getLongitude().toDouble()
        selectedItem = item
        Logger.i(TAG, "$lat, $lon")
    }

    fun getSelectedItemId() = sheet.item_title.text.toString()

    fun setButtonClickListener(listener: View.OnClickListener) {
        button.setOnClickListener(listener)
    }
}