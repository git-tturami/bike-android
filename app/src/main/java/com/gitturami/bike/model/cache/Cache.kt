package com.gitturami.bike.model.cache

import com.gitturami.bike.model.station.pojo.SummaryStation
import io.reactivex.Observable

interface Cache {
    fun store(value: SummaryStation)
    fun get(): Observable<List<SummaryStation>>

    fun clear()
    fun storeAll(list: List<SummaryStation>)
}