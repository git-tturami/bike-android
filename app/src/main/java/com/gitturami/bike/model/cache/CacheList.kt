package com.gitturami.bike.model.cache

import com.gitturami.bike.model.common.pojo.DefaultSummaryItem

class CacheList {
    val itemList: MutableList<DefaultSummaryItem> = mutableListOf()
    var requestedTime = 0L

    fun needCache(time: Long) = itemList.isEmpty() || time - requestedTime > 100000

    fun clear() {
        itemList.clear()
        requestedTime = 0L
    }
}