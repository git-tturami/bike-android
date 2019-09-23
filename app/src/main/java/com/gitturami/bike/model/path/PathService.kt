package com.gitturami.bike.model.path

import com.gitturami.bike.model.path.pojo.PathItem
import io.reactivex.Single
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface PathService {
        @Headers("appKey: 3826adf1-c99a-4e69-95c5-3763539465ea", "Content-Type: application/x-www-form-urlencoded")
        @POST("/tmap/routes/pedestrian")
        fun getPath(
                @Query("version")version: Int,
                @Query("format")format: String,
                @Query("startX")startX: Double,
                @Query("startY")startY: Double,
                @Query("endX")endX: Double,
                @Query("endY")endY: Double,
                @Query("startName")startName: String,
                @Query("endName")endName: String): Single<PathItem>

        @POST("/tmap/routes/pedestrian")
        fun getPath(
                @Query("version")version: Int,
                @Query("format")format: String,
                @Query("startX")startX: Double,
                @Query("startY")startY: Double,
                @Query("endX")endX: Double,
                @Query("endY")endY: Double,
                @Query("startName")startName: String,
                @Query("endName")endName: String,
                @Query("passList")passList: String): Single<PathItem>
}