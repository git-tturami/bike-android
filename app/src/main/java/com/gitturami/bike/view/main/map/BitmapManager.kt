package com.gitturami.bike.view.main.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import com.gitturami.bike.R

class BitmapManager(val context: Context) {
    val markerGreen: Bitmap by lazy {
        drawableToBitmap(R.drawable.ic_map_marker_material_green)
    }

    val markerRed: Bitmap by lazy {
        drawableToBitmap(R.drawable.ic_map_marker_material_red)
    }

    val markerYellow: Bitmap by lazy {
        drawableToBitmap(R.drawable.ic_map_marker_material_yellow)
    }

    private fun drawableToBitmap(resId: Int): Bitmap {
        val drawable = context.resources.getDrawable(resId, null)
        val bitmap = Bitmap.createBitmap(96, 96, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}