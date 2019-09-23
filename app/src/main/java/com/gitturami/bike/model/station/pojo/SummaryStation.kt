package com.gitturami.bike.model.station.pojo

import com.gitturami.bike.model.common.pojo.DefaultSummaryItem

data class SummaryStation(val shared: Int, val stationLatitude: String, val stationLongitude: String,
                     val stationId: String): DefaultSummaryItem {
    override fun getID(): String = stationId
    override fun getLatitude(): String = stationLatitude
    override fun getLongitude(): String = stationLongitude
}