package com.gitturami.bike.model.restaurant.pojo

import com.gitturami.bike.model.common.pojo.DefaultSummaryItem

data class SummaryRestaurant(val UPSO_NM: String, val X_CNTS: String, val Y_DNTS: String): DefaultSummaryItem {
    override fun getID(): String = UPSO_NM
    override fun getLatitude(): String = X_CNTS
    override fun getLongitude(): String = Y_DNTS
}