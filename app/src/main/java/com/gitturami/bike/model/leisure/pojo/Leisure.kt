package com.gitturami.bike.model.leisure.pojo

import com.gitturami.bike.model.common.pojo.DefaultItem

data class Leisure(
        val addr1: String,
        val areacode: Int,
        val cat1: String,
        val cat2: String,
        val cat3: String,
        val contentId: Int,
        val contenttypeid: Int,
        val createdtime: String,
        val firstimage: String,
        val firstimage2: String,
        val mapx: String,
        val mapy: String,
        val mlevel: Int,
        val modifiedtime: String,
        val readcount: Int,
        val sigungucode: Int,
        val title: String,
        val zipcode: String
): DefaultItem {
    override fun getName(): String = title
    override fun getTel(): String = mapx
    override fun getAddress(): String = addr1
}