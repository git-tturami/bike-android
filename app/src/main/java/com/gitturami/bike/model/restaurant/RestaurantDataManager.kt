package com.gitturami.bike.model.restaurant

import android.content.Context
import com.gitturami.bike.model.DataManager

class RestaurantDataManager(context: Context): DataManager(context) {
    private var api: RestaurantApi = retrofitConfig.getRetrofit().create(RestaurantApi::class.java)

    val allRestaurant= api.getAllRestaurant()

    val allLightRestaurant = api.getLightRestaurant()

    fun restaurantByName(name: String) = api.getRestaurantByName(name)
}