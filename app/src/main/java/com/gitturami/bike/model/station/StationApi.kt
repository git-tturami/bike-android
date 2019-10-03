package com.gitturami.bike.model.station

import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.StationResponse
import com.gitturami.bike.model.station.pojo.SummaryStation
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StationApi {
    // @GET("/stations/id")
    @GET("/stations/id")
    fun getStationById(@Query("id") id: String): Single<Station>

    @GET("/stations/name")
    fun getStationByName(@Query("name") name: String): Single<Station>

    // TODO : It is temporarily not enabled. (Server issue)
    @GET("/stations/close")
    fun getNearByStation(@Query("lat") lat: Float, @Query("lon") lon: Float): Single<StationResponse>

    @GET("/stations/enable")
    fun getEnableStation(): Observable<StationResponse>

    // @GET("/stations/summaries")
    @GET("/stations/summaries")
    fun getSummaryOfStation(): Observable<List<SummaryStation>>

    @GET("/stations/list")
    fun getAllStation(): Observable<List<Station>>
}
