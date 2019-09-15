package com.gitturami.bike.model.restaurant

import com.gitturami.bike.model.restaurant.pojo.RestaurantResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RestaurantApi {
    @GET("/restaurant/list")
    fun getAllRestaurant(): Observable<RestaurantResponse>
}