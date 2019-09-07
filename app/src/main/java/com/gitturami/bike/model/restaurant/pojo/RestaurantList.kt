package com.gitturami.bike.model.restaurant.pojo

import com.gitturami.bike.model.common.pojo.Result

data class RestaurantList(
        val list_total_count: Int,
        val RESULT: Result,
        val row: List<Restaurant>
)