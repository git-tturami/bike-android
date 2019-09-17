package com.gitturami.bike.model.restaurant

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.restaurant.pojo.Restaurant
import java.util.*

class RestaurantDataManager(context: Context): DataManager(context) {
    private var api: RestaurantApi = retrofitConfig.getRetrofit().create(RestaurantApi::class.java)

    val allRestaurant= api.getAllRestaurant()
}