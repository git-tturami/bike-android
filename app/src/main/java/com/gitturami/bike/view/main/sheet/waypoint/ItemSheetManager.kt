package com.gitturami.bike.view.main.sheet.waypoint

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_item_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_item_bottom_sheet.view.*
import kotlinx.coroutines.runBlocking

class ItemSheetManager(activity: MainActivity) {
    companion object {
        private const val TAG ="ItemSheetManager"
    }

    private val sheet: ConstraintLayout = activity.item_bottom_sheet
    private val behavior: BottomSheetBehavior<ConstraintLayout>
    private val button = activity.item_select_but
    private val mainImgView = activity.item_main_img
    var selectedItem: DefaultItem? = null
    var lat = 0.0
    var lon = 0.0

    init {
        behavior = BottomSheetBehavior.from(sheet)
        sheet.setOnTouchListener { view, motionEvent -> true }
        behavior.bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        mainImgView.clipToOutline = true
    }

    fun hideSheet() {
        behavior.isHideable = true
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        mainImgView.background = null
    }

    fun openSheet() {
        runBlocking {
            if (selectedItem?.getImage1Url() != null) {
                Logger.i(TAG, "set image1 view : ${selectedItem?.getImage1Url()}")
                Glide.with(sheet)
                        .load(selectedItem?.getImage1Url())
                        .into(mainImgView)
            } else {
                // TODO : when image is null, imgView's background must be set empty img.
            }
        }
        behavior.isHideable = false
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun setItem(item: DefaultItem) {
        sheet.item_title.text = item.getName()
        sheet.item_addr.text = item.getAddress()
        lat = item.getLatitude().toDouble()
        lon = item.getLongitude().toDouble()
        selectedItem = item
    }

    fun getSelectedItemId() = sheet.item_title.text.toString()

    fun setButtonClickListener(listener: View.OnClickListener) {
        button.setOnClickListener(listener)
    }
}