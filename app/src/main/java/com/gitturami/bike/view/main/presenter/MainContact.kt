package com.gitturami.bike.view.main.presenter

import com.gitturami.bike.adapter.contact.RecommendAdapterContact
import com.gitturami.bike.model.station.pojo.Station
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

interface MainContact {
    interface View {
        var tMapView: TMapView
        var recommendAdapterModel: RecommendAdapterContact.Model

        fun findPath(start: TMapPoint, end: TMapPoint)
        fun showToast(title: String)
        fun setMarker(x: Double, y: Double, station: Station)
    }

    interface Presenter {
        fun takeView(view: View)
        fun registerObserver()
        fun destroy()
    }
}