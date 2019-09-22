package com.gitturami.bike.model.station

import android.annotation.SuppressLint
import android.content.Context
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.cache.CacheManager
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.StationResponse
import com.gitturami.bike.model.station.pojo.SummaryStation
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StationDataManager(context: Context): DataManager(context) {
    companion object {
        val TAG = "StationDataManager"
    }
    private var api: StationApi = retrofitConfig.getRetrofit().create(StationApi::class.java)

    fun getStationById(id: String): Single<Station>? = api.getStationById(id)

    fun getStationByName(name: String): Single<Station>? = api.getStationByName(name)

    fun getNearByStation(lat: Float, lon: Float): Single<StationResponse>? =
            api.getNearByStation(lat, lon)

    val getEnableStationList: Observable<StationResponse> =
            api.getEnableStation()

    @SuppressLint("CheckResult")
    fun getAllSummaryStationList(cacheManager: CacheManager): Observable<List<SummaryStation>> {

        val allSummaryStationList: Observable<List<SummaryStation>>
                = if (cacheManager.isEmpty()) api.getSummaryOfStation()
                else cacheManager.get()
        Logger.i(TAG, "getAllSummaryStationList()")
        if (cacheManager.isEmpty()) allSummaryStationList.subscribeOn(Schedulers.io()).subscribe {
            list -> cacheManager.storeAll(list)
        }
        return allSummaryStationList
    }

    val allStationList: Observable<List<Station>> = api.getAllStation()
}