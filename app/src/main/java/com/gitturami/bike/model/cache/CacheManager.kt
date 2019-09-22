package com.gitturami.bike.model.cache

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.station.pojo.SummaryStation
import io.reactivex.Observable

class CacheManager: Cache {
    companion object {
        private const val TAG = "CacheManager"
    }

    private var dataList: MutableList<SummaryStation> = mutableListOf()

    override fun store(value: SummaryStation) {
        Logger.i(TAG, "store()")
        dataList.add(value)
    }

    override fun get(): Observable<List<SummaryStation>> {
        Logger.i(TAG, "get()")
        return Observable.just(dataList)
    }

    override fun storeAll(list: List<SummaryStation>) {
        Logger.i(TAG, "storeAll()")
        dataList.addAll(0, list)
    }

    fun isEmpty(): Boolean {
        if (dataList.size == 0) return true
        return false
    }

    override fun clear() {
        dataList.clear()
    }
}