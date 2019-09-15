package com.gitturami.bike.model.restaurant

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.restaurant.pojo.RestaurantResponse
import io.reactivex.Observable

class RestaurantDataManager(context: Context): DataManager(context) {
    private var api: RestaurantApi = retrofitConfig.getRetrofit().create(RestaurantApi::class.java)

    fun getAllRestaurant() : Observable<RestaurantResponse> = api.getAllRestaurant()
}