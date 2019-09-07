package com.gitturami.bike.model.restaurant

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.restaurant.pojo.RestaurantResponse

class RestaurantDataManager(context: Context): DataManager(context) {
    private var api: RestaurantApi = retrofitConfig.getRetrofit().create(RestaurantApi::class.java)

    fun getAllRestaurant() : RestaurantResponse = api.getAllRestaurant()
}