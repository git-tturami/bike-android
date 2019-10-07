package com.gitturami.bike.model.cafe.pojo

import com.gitturami.bike.model.common.pojo.DefaultSummaryItem

data class SummaryCafe(var NM: String, val XCODE: String, val YCODE: String): DefaultSummaryItem {
    override fun getIndex() = ""
    override fun getID(): String = NM
    override fun getLatitude(): String = XCODE
    override fun getLongitude(): String = YCODE
}