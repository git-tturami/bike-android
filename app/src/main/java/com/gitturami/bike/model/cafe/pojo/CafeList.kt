package com.gitturami.bike.model.cafe.pojo

import com.gitturami.bike.model.common.pojo.Result

data class CafeList(
    val list_total_count: Int,
    val RESULT: Result,
    val row: List<Cafe>
)