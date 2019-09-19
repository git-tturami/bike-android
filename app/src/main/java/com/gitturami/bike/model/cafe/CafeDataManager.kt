package com.gitturami.bike.model.cafe

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.cafe.pojo.Cafe
import com.gitturami.bike.model.cafe.pojo.SummaryCafe
import io.reactivex.Observable
import io.reactivex.Single

class CafeDataManager(context: Context): DataManager(context) {
    private var api: CafeApi = retrofitConfig.getRetrofit().create(CafeApi::class.java)

    val allCafe: Observable<List<Cafe>> = api.getAllCafe()
    val summariesCafe: Observable<List<SummaryCafe>> = api.getCafeSummaries()
    fun getCafeByName(name: String): Single<Cafe>? = api.getCafeByName(name)
}