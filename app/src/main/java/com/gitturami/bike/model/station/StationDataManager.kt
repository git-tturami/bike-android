package com.gitturami.bike.model.station

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.StationResponse

class StationDataManager(context: Context): DataManager(context) {
    private var api: StationApi = retrofitConfig.getRetrofit().create(StationApi::class.java)

    fun getStationById(id: String): Station? = api.getStationById(id).execute().body()

    fun getStationByName(name: String): Station? = api.getStationByName(name).execute().body()

    fun getNearByStation(lat: Float, lon: Float): StationResponse? =
            api.getNearByStation(lat, lon).execute().body()
}