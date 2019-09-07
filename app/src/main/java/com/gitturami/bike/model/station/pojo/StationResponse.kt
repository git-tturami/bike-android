package com.gitturami.bike.model.station.pojo

import com.gitturami.bike.model.common.pojo.DefaultDataList

data class StationResponse(
        val rentBikeStatus: DefaultDataList<Station>
)