package com.gitturami.bike.model.common.pojo

data class DataList<T>(
        val list_total_count: Int,
        val RESULT: Result,
        val row: List<T>
)