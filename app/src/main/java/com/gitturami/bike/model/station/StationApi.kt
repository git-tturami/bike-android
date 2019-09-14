package com.gitturami.bike.model.station

import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.StationResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StationApi {
    @GET("/stations/id")
    fun getStationById(@Query("id") id: String): Call<Station>

    @GET("/stations/name")
    fun getStationByName(@Query("name") name: String): Call<Station>

    // TODO : It is temporarily not enabled. (Server issue)
    @GET("/stations/close")
    fun getNearByStation(@Query("lat") lat: Float, @Query("lon") lon: Float): Call<StationResponse>

    @GET("/stations/enable")
    // fun getEnableStation(): Call<StationResponse>
    fun getEnableStation(): Single<StationResponse>

    @GET("/stations/list")
    fun getAllStation(): Observable<List<Station>>
}