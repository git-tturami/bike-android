package com.gitturami.bike.model.cafe

import android.content.Context
import com.gitturami.bike.model.DataManager

class CafeDataManager(context: Context): DataManager(context) {
    private var api: CafeApi = retrofitConfig.getRetrofit().create(CafeApi::class.java)

    val allCafe = api.getAllCafe()
}