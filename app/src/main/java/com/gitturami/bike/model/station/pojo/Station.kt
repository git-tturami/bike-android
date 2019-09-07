package com.gitturami.bike.model.station.pojo

data class Station(val rackToCnt: Int, val stationName: String, val parkingBikeToCnt: Int,
                   val shared: Int, val stationLatitude: String, val stationLongitude: String,
                   val stationId: String)