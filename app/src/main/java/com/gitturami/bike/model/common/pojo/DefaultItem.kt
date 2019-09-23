package com.gitturami.bike.model.common.pojo

interface DefaultItem {
    fun getName(): String
    fun getTel(): String
    fun getAddress(): String
    fun getLatitude(): String
    fun getLongitude(): String
    fun getImage1Url(): String?
    fun getImage2Url(): String?
}