package com.gitturami.bike.utils

import android.R.attr.radius

object GoogleMapProjection {
    fun lonToWorld(lon: Double): Double {
        val tiles = Math.pow(2.0, 0.0)
        val circumference = 256 * tiles
        val radius = circumference / (2 * Math.PI)
        val falseEasting = -1.0 * circumference / 2.0
        return (radius * Math.toRadians(lon)) - falseEasting
    }

    fun latToWorld(lat: Double): Double {
        val tiles = Math.pow(2.0, 0.0)
        val circumference = 256 * tiles
        val radius = circumference / (2 * Math.PI)
        val falseNorthing = circumference / 2.0;
        return (radius / 2.0 *
                Math.log((1.0 + Math.sin(Math.toRadians(lat))) /
                        (1.0 - Math.sin(Math.toRadians(lat)))) - falseNorthing) * -1;
    }

    fun worldToPixel(xWorld: Double, yWorld: Double, zoomLevel: Int): Point {
        val zoom = Math.pow(2.0, zoomLevel.toDouble()).toInt()
        val x = Math.round(xWorld * zoom).toInt()
//        val y =

        return Point(0.0, 0.0)
    }
}