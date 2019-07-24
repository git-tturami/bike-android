package com.gitturami.bike.view.main.presenter

import android.graphics.PointF
import android.widget.LinearLayout
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import java.util.ArrayList



class MainPresenter : MainContact.Presenter{
    private lateinit var tview: LinearLayout
    var Latitude: ArrayList<TMapMarkerItem>? = null
    var Longitude: ArrayList<TMapMarkerItem>? = null


    override fun takeView(view: LinearLayout) {
        this.tview = view
    }

}


