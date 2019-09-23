package com.gitturami.bike.model.path

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.path.pojo.PathItem
import io.reactivex.Single

class PathManager(context: Context): DataManager(context) {
    companion object {
        private const val TAG = "PathManager"
    }
    private var api: PathService = retrofitConfig.getTmapRetrofit().create(PathService::class.java)

    fun getPath(
            startX: Double,
            startY: Double,
            endX: Double,
            endY: Double,
            startName: String,
            endName: String): Single<PathItem>
            = api.getPath(1, "json", startX, startY, endX, endY, startName, endName)

    fun getPathIncludeWayPoint(
            startX: Double,
            startY: Double,
            endX: Double,
            endY: Double,
            startName: String,
            endName: String,
            passLists: String): Single<PathItem>
            = api.getPath(1, "json", startX, startY, endX, endY, startName, endName, passLists)

}