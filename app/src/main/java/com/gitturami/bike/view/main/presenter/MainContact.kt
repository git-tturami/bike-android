package com.gitturami.bike.view.main.presenter

import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.state.State
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

interface MainContact {
    interface View {
        var tMapView: TMapView

        fun findPath(start: Station, end: Station)
        fun clearPath()
        fun showToast(title: String)
        fun setMarker(x: Double, y: Double, station: Station)
        fun setStartSearchView(text: String)
        fun setFinishSearchView(text: String)
    }

    interface Presenter {
        fun takeView(view: View)
        fun registerObserver()
        fun setSearchView(text: String)
        fun getState(): State
        fun setState(state: State)
        fun setLocation(station: Station?)
        fun destroy()
    }
}