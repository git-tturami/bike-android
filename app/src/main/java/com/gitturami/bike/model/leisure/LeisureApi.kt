package com.gitturami.bike.model.leisure

import com.gitturami.bike.model.leisure.pojo.Leisure
import com.gitturami.bike.model.leisure.pojo.LeisureResponse
import com.gitturami.bike.model.leisure.pojo.SummaryLeisure
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LeisureApi {
    @GET("/leisure/list")
    fun getAllLeisure(): LeisureResponse

    @GET("/leisure/parks")
    fun getAllPark(): LeisureResponse

    @GET("/leisure/cultures")
    fun getAllCultures(): LeisureResponse

    @GET("/leisure/festivals")
    fun getAllFestivals(): LeisureResponse

    @GET("/leisure/leports")
    fun getAllLeports(): LeisureResponse

    @GET("/leisure/courses")
    fun getAllCourses(): LeisureResponse

    @GET("/leisure/hotels")
    fun getAllHotels(): LeisureResponse

    @GET("/leisure/shopping")
    fun getAllShopping(): LeisureResponse

    @GET("/leisure/foods")
    fun getAllFoods(): LeisureResponse

    @GET("/leisure/summaries")
    fun getLeisureSummaries(): Observable<List<SummaryLeisure>>

    @GET("/leisure/terrain/summaries")
    fun getTerrainSummaries(): Observable<List<SummaryLeisure>>

    @GET("/leisure/name")
    fun getLeisureByName(@Query("name") name : String) : Single<Leisure>

    @GET("/leisure/courses/summaries")
    fun getCourseSummaries(): Observable<List<SummaryLeisure>>

    @GET("/leisure/shopping/summaries")
    fun getShoppingSummaries(): Observable<List<SummaryLeisure>>
}