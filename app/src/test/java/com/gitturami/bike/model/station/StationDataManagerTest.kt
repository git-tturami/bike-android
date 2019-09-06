package com.gitturami.bike.model.station

import android.content.Context
import com.gitturami.bike.R
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
        Mockito.`when`(context.getString(R.string.serverAddr)).thenReturn("http://127.0.0.1")
        stationDataManager = StationDataManager(context)
    }

    @Test fun retrofitConfigMustBeCreated() {
        System.out.println(stationDataManager.retrofitConfig)
        assertNotNull(stationDataManager.retrofitConfig)
    }
}