package com.gitturami.bike.model.station

import android.content.Context
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.StationResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class StationDataManager(context: Context): DataManager(context) {
    companion object {
        val TAG = "StationDataManager"
    }
    private var api: StationApi = retrofitConfig.getRetrofit().create(StationApi::class.java)

    fun getStationById(id: String): Station? = api.getStationById(id).execute().body()

    fun getStationByName(name: String): Station? = api.getStationByName(name).execute().body()

    fun getNearByStation(lat: Float, lon: Float): StationResponse? =
            api.getNearByStation(lat, lon).execute().body()

    fun getEnableStationList(): Observable<Station?>
            = api.getEnableStation().execute().body()!!.rentBikeStatus.row.toObservable()
            .subscribeOn(Schedulers.io())
            .doOnNext{
                Logger.i(TAG, it.stationId)
            }
            .doOnComplete{
                Logger.i(TAG, "complete()")
            }
            .doOnError{
                Logger.e(TAG, "err: ${it.message}")
            }
            .observeOn(AndroidSchedulers.mainThread())

}