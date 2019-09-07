package com.gitturami.bike.model.station.pojo

import com.gitturami.bike.model.common.pojo.DataList

data class StationResponse(
        val rentBikeStatus: DataList<Station>
)