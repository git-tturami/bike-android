package com.gitturami.bike.view.main

import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.model.common.pojo.DefaultSummaryItem
import com.gitturami.bike.model.path.pojo.PathItem
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.map.ItemType
import com.gitturami.bike.view.main.state.State

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
        fun setItem(item: DefaultItem)
        fun markPath(pathItem: PathItem): Int
        fun onCompleteMarking()
        fun setMarker(type: ItemType, item: DefaultSummaryItem, onClick: () -> Unit)
        fun setStartMarker(station: Station)
        fun setFinishMarker(station: Station)
        fun hideStartMarker()
        fun hideFinishMarker()
        fun hideCategoryMarkers()
        fun setMarkerColorByShared(id: String, shared: Int)
        fun setStartSearchView(text: String)
        fun setFinishSearchView(text: String)
        fun setSelectBottomSheet(station: Station)
        fun hideStationSheet()
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
        fun startLoading()
        fun endLoading()

        fun addWayPointItem(item: DefaultSummaryItem)
        fun setWayPointMarker(item: DefaultItem)
        fun clearWayPointRecyclerItemList()
        fun showScreenshotDialog()
        fun hideScreenshotDialog()
        fun getState(): State
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
        fun setWayPointAndFindPath(wayPoint: DefaultItem)
        fun requestDetailItem(type: ItemType, param: String)
        fun destroy()
        fun getStartStationName(): String?
        fun getEndStationName(): String?
        fun getWayPointName(): String?
        fun getDistance(): Int
    }
}