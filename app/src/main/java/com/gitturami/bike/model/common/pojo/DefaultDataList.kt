package com.gitturami.bike.model.common.pojo

data class DefaultDataList<T>(
        val list_total_count: Int,
        val DefaultRESULT: DefaultResult,
        val row: List<T>
)