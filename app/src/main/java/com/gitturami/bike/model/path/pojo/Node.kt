package com.gitturami.bike.model.path.pojo

import com.google.gson.JsonObject

data class Node(
        val type: String,
        val geometry: Geometry,
        val properties: JsonObject
)