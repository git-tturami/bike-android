package com.gitturami.bike.model.restaurant.pojo

import com.gitturami.bike.model.common.pojo.DefaultItem

data class Restaurant(
        val CRTFC_UPSO_MGT_SNO: String,
        val UPSO_SNO: String,
        val UPSO_NM: String,
        val CGG_CODE: String,
        val CGG_CODE_NM: String,
        val COB_CODE_NM: String,
        val BIZCND_CODE_NM: String,
        val OWNER_NM: String,
        val CRTFC_GBN: String,
        val CRTFC_GBN_NM: String,
        val CRTFC_GHR_NM: String,
        val CRTFC_CHR_ID: String,
        val CRTFC_YMD: String,
        val USE_YN: String,
        val MAP_INDICT_YN: String,
        val CRTFC_CLASS: String,
        val Y_DNTS: String,
        val X_CNTS: String,
        val TEL_NO: String,
        val RDN_DETAIL_ADDR: String,
        val RDN_ADDR_CODE: String,
        val RDN_CODE_NM: String,
        val BIZCND_CODE: String,
        val COB_CODE: String,
        val CRTFC_SNO: String,
        val CRT_TIME: String,
        val CRT_USR: String,
        val UPD_TIME: String,
        val FOOD_MENU: String,
        val GNT_NO: String,
        val CRTFC_YN: String
) : DefaultItem {
    override fun getName(): String = UPSO_NM
    override fun getTel(): String = TEL_NO
    override fun getAddress(): String = RDN_DETAIL_ADDR
}