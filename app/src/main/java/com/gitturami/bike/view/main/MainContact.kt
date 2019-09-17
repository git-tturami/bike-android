package com.gitturami.bike.view.main

import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.state.State

interface MainContact {
    interface View {
        fun getPresenter(): Presenter
        fun findPath(start: Station, end: Station)
        fun hidePath()
        fun clearPath()
        fun showToast(title: String)
        fun setMarkers()
        fun setMarker(x: Double, y: Double, station: Station)
        fun onCompleteMarking()
        fun changeMarker(station: Station)
        fun setStartSearchView(text: String)
        fun setFinishSearchView(text: String)
        fun setSelectDialogContants(station: Station)
        fun hideStationMarker()
        fun hideCategorySheet()
        fun collapseCategorySheet()
        fun hideWayPointSheet()
        fun halfWayPointSheet()
        fun expandWayPointSheet()
    }

    interface Presenter {
        fun takeView(view: View)
        fun setMarkers()
        fun setSearchView(text: String)
        fun getState(): State
        fun setState(state: State)
        fun setLocation(station: Station?)
        fun destroy()
    }
}