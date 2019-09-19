package com.gitturami.bike.model.path.pojo

import com.google.gson.JsonElement

data class Geometry(
        val type: String,
        val coordinates: JsonElement
)