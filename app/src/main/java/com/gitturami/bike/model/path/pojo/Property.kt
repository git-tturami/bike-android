package com.gitturami.bike.model.path.pojo

data class Property(
        val index: Int,
        val pointIndex: Int,
        val name: String,
        val guidePointName: String,
        val description: String,
        val direction: String,
        val intersectionName: String,
        val nearPoiName: String,
        val nearPoiX: String,
        val nearPoiY: String,
        val crossName: String,
        val turnType: Int,
        val pointType: String,

        val lineIndex:Int,
        val roadName: String,
        val distance: String,
        val time: Int,
        val roadType: Int,
        val categoryRoadType: Int,
        val facilityType: Int,
        val facilityName: String
)