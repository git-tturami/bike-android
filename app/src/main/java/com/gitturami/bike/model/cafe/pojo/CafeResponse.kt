package com.gitturami.bike.model.cafe.pojo

import com.gitturami.bike.model.common.pojo.DataList

data class CafeResponse(
        val coffeeShopInfo: DataList<Cafe>
)