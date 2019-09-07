package com.gitturami.bike.model.station.pojo

import com.gitturami.bike.model.common.pojo.Result

data class StationList(val list_total_count: Int, val RESULT: Result, val row: List<Station>)