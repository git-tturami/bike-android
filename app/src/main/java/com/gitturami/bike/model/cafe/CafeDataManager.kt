package com.gitturami.bike.model.cafe

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.cafe.pojo.Cafe

class CafeDataManager(context: Context): DataManager(context) {
    private var api: CafeApi = retrofitConfig.getRetrofit().create(CafeApi::class.java)

    val allCafe = api.getAllCafe()
    val summariesCafe = api.getCafeSummaries()
    fun getCafeByName(name: String): Cafe? = api.getCafeByName(name).execute().body()
}