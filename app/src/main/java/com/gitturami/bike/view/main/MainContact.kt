package com.gitturami.bike.view.main

import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.SummaryStation
import com.gitturami.bike.view.main.state.State

interface MainContact {
    interface View {
        fun getPresenter(): Presenter
        fun findPath(start: Station, end: Station)
        fun hidePath()
        fun clearPath()
        fun showToast(title: String)
        fun setStationMarkers()
        fun setRestaurantMarkers(x: Double, y: Double, restaurant: Restaurant)
        fun setMarker(x: Double, y: Double, station: SummaryStation)
        fun setMarker(x: Double, y: Double, restaurant: Restaurant)
        fun onCompleteMarking()
        fun changeMarker(station: Station)
        fun setStartSearchView(text: String)
        fun setFinishSearchView(text: String)
        fun setSelectDialogContants(id: String)
        fun setSelectBottomSheet(station: Station)
        fun hideAllMarkers()
        fun hideCategorySheet()
        fun collapseCategorySheet()
        fun collapseWayPointSheet()
        fun hideWayPointSheet()
        fun halfWayPointSheet()
        fun expandWayPointSheet()
        fun collapseItemSheet()
        fun hideItemSheet()

        fun addWayPointItem(item: DefaultItem)
    }

    interface Presenter {
        fun takeView(view: View)
        fun loadDetailInfoOfStation(id: String)
        fun setStationMarkers()
        fun setCafeMarkers()
        fun setRestaurantMarkers()
        fun setSearchView(text: String)
        fun getState(): State
        fun setState(state: State)
        fun setLocation(station: Station?)
        fun destroy()
    }
}