package com.gitturami.bike.view.main.sheet.waypoint

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.common.pojo.DefaultItem
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
    private val mainImgView = activity.item_main_img
    private val subImgView1 = activity.item_sub_img1
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
            behavior.peekHeight = 450
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun setItem(item: DefaultItem) {
        sheet.item_title.text = item.getName()
        sheet.item_addr.text = item.getAddress()
        lat = item.getLatitude().toDouble()
        lon = item.getLongitude().toDouble()
        selectedItem = item

        if (selectedItem?.getImage1Url() != null) {
            Logger.i(TAG, "set image1 view : ${selectedItem?.getImage1Url()}")
            Glide.with(sheet)
                    .load(selectedItem?.getImage1Url())
                    .override(228, 155)
                    .into(mainImgView)
        } else {
            // TODO : when image is null, imgView's background must be set empty img.
        }

        if (selectedItem?.getImage2Url() != null) {
            Logger.i(TAG, "set image2 view : ${selectedItem?.getImage2Url()}")
            Glide.with(sheet)
                    .load(selectedItem?.getImage2Url())
                    .override(113, 77)
                    .into(subImgView1)
        } else {
            // TODO : when image is null, imgView's background must be set empty img.
        }

        Logger.i(TAG, "$lat, $lon")
    }

    fun getSelectedItemId() = sheet.item_title.text.toString()

    fun setButtonClickListener(listener: View.OnClickListener) {
        button.setOnClickListener(listener)
    }
}