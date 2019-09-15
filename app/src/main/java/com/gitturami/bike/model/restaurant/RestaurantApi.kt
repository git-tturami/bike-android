package com.gitturami.bike.model.restaurant

import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.model.restaurant.pojo.RestaurantResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RestaurantApi {
    @GET("/restaurants/list")
    fun getAllRestaurant(): Observable<List<Restaurant>>
}