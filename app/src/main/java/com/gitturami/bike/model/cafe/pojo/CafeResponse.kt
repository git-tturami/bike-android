package com.gitturami.bike.model.cafe.pojo

import com.gitturami.bike.model.common.pojo.DefaultDataList

data class CafeResponse(
        val coffeeShopInfo: DefaultDataList<Cafe>
)