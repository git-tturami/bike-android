package com.gitturami.bike.model.station

import android.content.Context
import com.gitturami.bike.R
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.StationResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StationDataManagerTest {

    private lateinit var stationDataManager: StationDataManager
    @Mock private lateinit var context: Context

    @Before fun setUp() {
        Mockito.`when`(context.getString(R.string.serverAddr)).thenReturn("http://ec2-15-164-94-0.ap-northeast-2.compute.amazonaws.com:8080")
        stationDataManager = StationDataManager(context)
    }

    @Test fun retrofitConfigMustBeCreated() {
        println(stationDataManager.retrofitConfig)
        assertNotNull(stationDataManager.retrofitConfig)
    }

    @Test fun getStationByIdMustGetResponse() {
        val stationId = "ST-10"
        val stationResponse: Station? = stationDataManager.getStationById(stationId)
        println(stationResponse)
        assertNotNull(stationResponse)
        assertEquals(stationId, stationResponse?.stationId)

    }

    @Test fun getStationByNameMustGetResponse() {
        val stationName ="108. 서교동 사거리"
        val stationResponse: Station? = stationDataManager.getStationByName(stationName)
        println(stationResponse)
        assertNotNull(stationResponse)
        assertEquals(stationName, stationResponse?.stationName)
    }

    @Test fun getAllStationMustGetResponse() {
        val stationResponse = stationDataManager.allStationList
        stationResponse.subscribe(
                {it -> println(it)},
                {err -> println(err)}
        )
    }

    @Test fun getNearByStationMustBeGetResponse() {
        val lat = 37.5050881f
        val long = 126.9571012f
        val stationResponse: StationResponse? = stationDataManager.getNearByStation(lat, long)
        println(stationResponse)
        assertNotNull(stationResponse)
    }
}