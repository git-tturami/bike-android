package com.gitturami.bike.model.restaurant.pojo

import com.gitturami.bike.model.common.pojo.DefaultSummaryItem

data class SummaryRestaurant(val UPSO_NM: String, val X_CNTS: String, val Y_DNTS: String): DefaultSummaryItem {
    override fun getID(): String = UPSO_NM
    override fun getLatitude(): String = Y_DNTS
    override fun getLongitude(): String = X_CNTS
}