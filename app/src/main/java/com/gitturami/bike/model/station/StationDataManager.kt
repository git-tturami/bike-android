package com.gitturami.bike.model.station

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.StationResponse
import com.gitturami.bike.model.station.pojo.SummaryStation
import io.reactivex.Observable
import io.reactivex.Single

class StationDataManager(context: Context): DataManager(context) {
    companion object {
        val TAG = "StationDataManager"
    }
    private var api: StationApi = retrofitConfig.getRetrofit().create(StationApi::class.java)

    fun getStationById(id: String): Single<Station>? = api.getStationById(id)

    fun getStationByName(name: String): Station? = api.getStationByName(name).execute().body()

    fun getNearByStation(lat: Float, lon: Float): StationResponse? =
            api.getNearByStation(lat, lon).execute().body()

    val getEnableStationList: Observable<StationResponse> =
            api.getEnableStation()

    val allSummaryStationList: Observable<List<SummaryStation>> = api.getSummaryOfStation()

     val allStationList: Observable<List<Station>> = api.getAllStation()
}