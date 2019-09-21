package com.gitturami.bike.view.main

import com.gitturami.bike.model.cafe.pojo.Cafe
import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.model.common.pojo.DefaultSummaryItem
import com.gitturami.bike.model.leisure.pojo.Leisure
import com.gitturami.bike.model.path.pojo.PathItem
import com.gitturami.bike.model.leisure.pojo.SummaryLeisure
import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.model.restaurant.pojo.SummaryRestaurant
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.SummaryStation
import com.gitturami.bike.view.main.map.ItemType
import com.gitturami.bike.view.main.state.State
import com.gitturami.bike.model.cafe.pojo.SummaryCafe as SummaryCafe

interface MainContact {
    interface View {
        fun getPresenter(): Presenter
        fun hidePath()
        fun clearPath()
        fun showToast(title: String)
        fun setStationMarkers()
        fun setRestaurantMarkers()
        fun setCafeMarkers()
        fun setLeisureMarkers()
        fun setTerrainMarkers()
        fun setMarker(x: Double, y: Double, station: SummaryStation)
        fun markPath(pathItem: PathItem)
        fun setMarker(type: ItemType, x: Double, y: Double, item: DefaultSummaryItem)
        fun onCompleteMarking()
        fun changeMarker(station: Station)
        fun setStartSearchView(text: String)
        fun setFinishSearchView(text: String)
        fun loadDetailInfoOfStation(id: String)
        fun loadDetailInfoOfCafe(name: String)
        fun loadDetailInfoOfLeisure(title: String)
        fun loadDetailInfoOfRestaurant(name: String)
        fun setSelectBottomSheet(station: Station)
        fun setItemBottomSheet(item: DefaultItem)
        fun hideAllMarkers()
        fun hideCategorySheet()
        fun collapseCategorySheet()
        fun collapseWayPointSheet()
        fun hideWayPointSheet()
        fun halfWayPointSheet()
        fun expandWayPointSheet()
        fun collapseItemSheet()
        fun hideItemSheet()

        fun addWayPointItem(item: DefaultSummaryItem)
    }

    interface Presenter {
        fun takeView(view: View)
        fun loadDetailInfoOfStation(id: String)
        fun loadDetailInfoOfCafe(name: String)
        fun loadDetailInfoOfLeisure(title: String)
        fun loadDetailInfoOfRestaurant(name: String)
        fun setStationMarkers()
        fun setCafeMarkers()
        fun setRestaurantMarkers()
        fun setLeisureMarkers()
        fun setTerrainMarkers()
        fun setSearchView(text: String)
        fun getState(): State
        fun setState(state: State)
        fun setLocation(station: Station?)
        fun findPath(start: Station, end: Station)
        fun destroy()
    }
}