package com.gitturami.bike.model.station.pojo

import com.gitturami.bike.model.common.pojo.DefaultSummaryItem

data class SummaryStation(val idx: Int, val shared: Int, val stationLatitude: String, val stationLongitude: String,
                     val stationId: String): DefaultSummaryItem {
    override fun getIndex() = idx.toString()
    override fun getID(): String = idx.toString()
    override fun getLatitude(): String = stationLatitude
    override fun getLongitude(): String = stationLongitude
}