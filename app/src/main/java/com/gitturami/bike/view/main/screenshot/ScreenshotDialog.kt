package com.gitturami.bike.view.main.screenshot

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import com.gitturami.bike.R
import com.gitturami.bike.view.main.MainActivity
import kotlinx.android.synthetic.main.layout_screenshot.*

class ScreenshotDialog(activity: MainActivity) {
    private val dialog = AppCompatDialog(activity)

    private val resultImageView by lazy {
        dialog.result_image
    }

    private val pathTextView by lazy {
        dialog.result_path_text
    }

    private val arriveTextView by lazy {
        dialog.result_arrive_text
    }

    private val distanceTextView by lazy {
        dialog.result_distance_text
    }

    init {
        dialog.setContentView(R.layout.layout_screenshot)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
    }

    fun show(screenshot: Bitmap?, start: String, wayPoint: String, end: String, distance: String?) {
        setPathText(start, wayPoint)
        if (screenshot != null) {
            setResultImage(screenshot)
        }
        setArriveText(end)
        if (distance != null) {
            setDistanceText(distance)
        }
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }

    private fun setResultImage(bitMap: Bitmap) {
        resultImageView.setImageBitmap(bitMap)
    }

    private fun setPathText(start: String, wayPoint: String) {
        val path = "$start > $wayPoint >"
        pathTextView.text = path
    }

    private fun setArriveText(end: String) {
        arriveTextView.text = end
    }

    private fun setDistanceText(distance: String) {
        distanceTextView.text = distance
    }
}