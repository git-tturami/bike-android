package com.gitturami.bike.model.cafe

import com.gitturami.bike.model.cafe.pojo.Cafe
import com.gitturami.bike.model.cafe.pojo.CafeResponse
import com.gitturami.bike.model.cafe.pojo.SummaryCafe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface CafeApi {
    @GET("/cafes/list")
    fun getAllCafe(): Observable<List<Cafe>>

    @GET("/cafes/summaries")
    fun getCafeSummaries(): Observable<List<SummaryCafe>>

    @GET("/cafes/name")
    fun getCafeByName(@Query("name") name: String): Single<Cafe>
}