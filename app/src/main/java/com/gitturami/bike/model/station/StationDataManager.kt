package com.gitturami.bike.model.station

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.StationResponse
import io.reactivex.Observable

class StationDataManager(context: Context): DataManager(context) {
    companion object {
        val TAG = "StationDataManager"
    }
    private var api: StationApi = retrofitConfig.getRetrofit().create(StationApi::class.java)

    fun getStationById(id: String): Station? = api.getStationById(id).execute().body()

    fun getStationByName(name: String): Station? = api.getStationByName(name).execute().body()

    fun getNearByStation(lat: Float, lon: Float): StationResponse? =
            api.getNearByStation(lat, lon).execute().body()

    val getEnableStationList: Observable<StationResponse> =
            api.getEnableStation()

    val allStationList: Observable<List<Station>> = api.getAllStation()
}