package com.gitturami.bike.view.main.sheet.result

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.gitturami.bike.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
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

    fun setItem(context: Context, start: String, end: String, wayPoint: String) {
        layout.addView(createTextView(context, start))
        layout.addView(createTextView(context, wayPoint))
        layout.addView(createTextView(context, end))
    }

    private fun createTextView(context: Context, text: String): TextView {
        val tv = TextView(context)
        tv.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        tv.text = text
        tv.textSize = 12f
        return tv
    }

    fun openSheet() {
        behavior.isHideable = false
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun hideSheet() {
        behavior.isHideable = true
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}