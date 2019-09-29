package com.gitturami.bike.view.main.map.listener

import android.graphics.PointF
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.state.State
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import java.util.ArrayList

class TMapListener(private val activity: MainActivity): TMapView.OnClickListenerCallback {
    private lateinit var dragCheckPoint: PointF

    override fun onPressUpEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF): Boolean {
        if (!isDrag(dragCheckPoint, p3) && checkState()) {
            activity.onBackPressed()
        }
        return true
    }

    override fun onPressEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF): Boolean {
        dragCheckPoint = p3
        return true
    }

    private fun isDrag(p1: PointF, p2: PointF): Boolean {
        if (kotlin.math.abs(p1.x - p2.x) > 10.0 || kotlin.math.abs(p1.y - p2.y) > 10.0) return true
        return false
    }

    private fun checkState(): Boolean {
        when(activity.getState()){
            State.SHOW_START -> return true
            State.SHOW_FINISH -> return true
            State.SELECT_WAYPOINT -> return true
        }

        return false
    }
}