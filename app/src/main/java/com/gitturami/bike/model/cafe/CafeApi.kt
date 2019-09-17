package com.gitturami.bike.model.cafe

import com.gitturami.bike.model.cafe.pojo.Cafe
import com.gitturami.bike.model.cafe.pojo.CafeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

interface CafeApi {
    @GET("/cafes/list")
    fun getAllCafe(): Observable<List<Cafe>>
}