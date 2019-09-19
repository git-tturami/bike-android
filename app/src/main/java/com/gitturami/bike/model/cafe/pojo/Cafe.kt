package com.gitturami.bike.model.cafe.pojo

import com.gitturami.bike.model.common.pojo.DefaultItem

data class Cafe(
        val NM: String,
        val ADDR_OLD: String,
        val ADDR: String,
        val OPEN_DT: String,
        val STATE: String,
        val STOP_DT: String,
        val SUSPENSION_START_DT: String,
        val SUSPENSION_END_DT: String,
        val RE_OPEN_DT: String,
        val AREA: String,
        val POST: String,
        val WATER_SUPPLY: String,
        val MALE_NUM: String,
        val YEAR: String,
        val USEDBYMANYPEOPLE: String,
        val GRADE_NM: String,
        val TOTAL_SCALE: String,
        val FEMALE_NUM: String,
        val BUZPLC_ARND_NM: String,
        val HYGIENE_TYPE: String,
        val HYGIENE_CONDITIONS: String,
        val TEL: String,
        val XCODE: String,
        val YCODE: String,
        val PERMISSION_NO: String,
        val DETAIL_STAT_NM: String
): DefaultItem {
    override fun getName(): String = NM
    override fun getTel(): String = XCODE
}