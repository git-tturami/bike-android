package com.gitturami.bike.view.main.listener

import android.graphics.PointF
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.presenter.MainContact
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import java.util.ArrayList
import kotlin.math.abs

class TMapOnClickListener(private var view: MainContact.View)
    : TMapView.OnClickListenerCallback {

    companion object {
        val TAG = "TMapOnClickListener"
    }

    private lateinit var currentClickedMapPoint: TMapPoint
    private lateinit var dragCheckPoint: PointF

    override fun onPressEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
        Logger.i(TAG, "onPressEvent()")
        dragCheckPoint = p3!!
        return true
    }

    override fun onPressUpEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
        if (!isDrag(dragCheckPoint, p3!!)) {
            currentClickedMapPoint = p2!!
            val tItem = TMapMarkerItem()
            tItem.tMapPoint = TMapPoint(currentClickedMapPoint.latitude, currentClickedMapPoint.longitude)
            tItem.visible = TMapMarkerItem.VISIBLE
            view.tMapView.addMarkerItem(tItem.id, tItem)
        }
        return true
    }


    private fun isDrag(p1: PointF, p2: PointF): Boolean {
        if (abs(p1.x - p2.x) > 10.0 || abs(p1.y - p2.y) > 10.0) return true
        return false
    }
}