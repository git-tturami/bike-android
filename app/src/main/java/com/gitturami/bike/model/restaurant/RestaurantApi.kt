package com.gitturami.bike.model.restaurant

import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.model.restaurant.pojo.SummaryRestaurant
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {
    @GET("/restaurants/list")
    fun getAllRestaurant(): Observable<List<Restaurant>>

    @GET("/restaurants/summaries")
    fun getLightRestaurant(): Observable<List<SummaryRestaurant>>

    @GET("/restaurants/name")
    fun getRestaurantByName(@Query("name") name: String): Single<Restaurant>
}