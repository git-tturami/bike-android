package com.gitturami.bike.view.main.sheet.result

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.gitturami.bike.R
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.map.ItemType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_result_list.view.*
import kotlinx.android.synthetic.main.layout_result_sheet.*
import kotlinx.android.synthetic.main.layout_result_sheet.view.*

class ResultSheet(activity: MainActivity) {
    companion object {
        val TAG = "ResultSheet"
    }

    private val sheet: ConstraintLayout = activity.result_bottom_sheet
    private val layout: LinearLayout
    private val behavior: BottomSheetBehavior<ConstraintLayout>

    init {
        behavior = BottomSheetBehavior.from(sheet)
        behavior.isHideable = true
        behavior.bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        layout = sheet.result_layout
    }

    fun setItem(context: Context,
                start: String,
                end: String,
                wayPoint: String,
                distance: Int) {
        hideSheet()
        layout.removeAllViews()
        layout.addView(createItemLayout(context, start, ItemType.DEPARTURE))
        layout.addView(createItemLayout(context, "", ItemType.DOT))
        layout.addView(createItemLayout(context, wayPoint, ItemType.LAYOVER))
        layout.addView(createItemLayout(context, "", ItemType.DOT))
        layout.addView(createItemLayout(context, end, ItemType.ARRIVAL))
        val d = when (distance >= 1000) {
            true -> {
                "${distance/1000}.${distance%1000}km"
            }
            false -> {
                "${distance}m"
            }
        }
        sheet.distance_result_bottom_sheet.text = d
    }

    private fun createItemLayout(context: Context, text: String, type: ItemType): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.layout_result_list, sheet, false)

        when (type) {
            ItemType.DEPARTURE -> {
                layout.img_result_list.setImageDrawable(
                        context.resources.getDrawable(R.drawable.ic_departure, null)
                )
            }
            ItemType.ARRIVAL -> {
                layout.img_result_list.setImageDrawable(
                        context.resources.getDrawable(R.drawable.ic_arrival, null)
                )
            }
            ItemType.LAYOVER -> {
                layout.img_result_list.setImageDrawable(
                        context.resources.getDrawable(R.drawable.ic_layover, null)
                )
            }
            ItemType.DOT -> {
                layout.img_result_list.setImageDrawable(
                        context.resources.getDrawable(R.drawable.ic_dot, null)
                )
            }
            else -> {

            }
        }
        layout.text_result_list.text = text
        layout.text_result_list.setTextColor(Color.BLACK)
        return layout
    }

    fun openSheet() {
        hideSheet()
        behavior.isHideable = false
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun hideSheet() {
        behavior.isHideable = true
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}