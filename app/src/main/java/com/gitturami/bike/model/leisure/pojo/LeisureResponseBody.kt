package com.gitturami.bike.model.leisure.pojo

data class LeisureResponseBody(
        val items: List<Leisure>,
        val numOfRows: Int,
        val totalCount: Int
)