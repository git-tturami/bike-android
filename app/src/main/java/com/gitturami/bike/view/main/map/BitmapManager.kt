package com.gitturami.bike.view.main.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import com.gitturami.bike.R

class BitmapManager(val context: Context) {
    val greenMarker: Bitmap by lazy {
        drawableToBitmap(R.drawable.ic_map_marker_material_green)
    }

    val redMarker: Bitmap by lazy {
        drawableToBitmap(R.drawable.ic_map_marker_material_red)
    }

    val yellowMarker: Bitmap by lazy {
        drawableToBitmap(R.drawable.ic_map_marker_material_yellow)
    }

    val restaurantMarker: Bitmap by lazy {
        drawableToMarker(R.drawable.ic_mark_restaurant1)
    }

    val cafeMarker: Bitmap by lazy {
        drawableToMarker(R.drawable.ic_mark_cafe1)
    }

    val leisureMarker: Bitmap by lazy {
        drawableToMarker(R.drawable.ic_mark_festival1)
    }

    val terrainMarker: Bitmap by lazy {
        drawableToMarker(R.drawable.ic_mark_terrain1)
    }

    private fun drawableToBitmap(resId: Int): Bitmap {
        val drawable = context.resources.getDrawable(resId, null)
        val bitmap = Bitmap.createBitmap(96, 96, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun drawableToMarker(resId: Int): Bitmap {
        val drawable = context.resources.getDrawable(resId, null)
        val bitmap = Bitmap.createBitmap(80, 96, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}