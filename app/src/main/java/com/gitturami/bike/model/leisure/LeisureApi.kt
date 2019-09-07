package com.gitturami.bike.model.leisure

import com.gitturami.bike.model.leisure.pojo.LeisureResponse
import retrofit2.http.GET

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
}