package com.gitturami.bike.model.leisure.pojo

import com.gitturami.bike.model.common.pojo.DefaultSummaryItem

data class SummaryLeisure(val title: String
                          , val mapx: String, val mapy: String): DefaultSummaryItem
{
    override fun getID(): String = title
    override fun getLatitude(): String = mapy
    override fun getLongitude(): String = mapx
}