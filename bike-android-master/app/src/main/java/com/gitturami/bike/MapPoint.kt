package com.gitturami.bike

class MapPoint {
    var name: String? = null
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()

    constructor() : super() {}
    constructor(Name: String, latitude: Double, longitude: Double) {
        this.name = Name
        this.latitude = latitude
        this.longitude = longitude
    }


}
