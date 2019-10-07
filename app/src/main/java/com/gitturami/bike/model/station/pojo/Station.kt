package com.gitturami.bike.model.station.pojo

data class Station(val index: Int, val rackTotCnt: Int, val stationName: String, val parkingBikeTotCnt: Int,
                   val shared: Int, val stationLatitude: String, val stationLongitude: String,
                   val stationId: String)