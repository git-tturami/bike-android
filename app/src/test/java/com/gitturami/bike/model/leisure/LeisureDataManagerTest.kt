package com.gitturami.bike.model.leisure

import android.content.Context
import com.gitturami.bike.R
import com.gitturami.bike.model.leisure.pojo.Leisure
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LeisureDataManagerTest{
    private lateinit var leisureDataManager: LeisureDataManager
    @Mock private lateinit var context: Context

    @Before
    fun setUp(){
        Mockito.`when`(context.getString(R.string.serverAddr)).thenReturn("http://ec2-15-164-94-0.ap-northeast-2.compute.amazonaws.com:8080")
        leisureDataManager = LeisureDataManager(context)
    }

    @Test
    fun retrofitConfigMustBeCreated(){
        println(leisureDataManager.retrofitConfig)
        Assert.assertNotNull(leisureDataManager.retrofitConfig)
    }

    @Test
    fun getLeisureSummariesGetResponse(){
        val leisureResponse = leisureDataManager.summariesLeisure
        leisureResponse.subscribe(
                {it -> println(it)},
                {err -> println(err)}
        )
    }

    @Test
    fun getTerrainSummariesGetResponse(){
        val terrainResponse = leisureDataManager.summariesTerrain
        terrainResponse.subscribe(
                {it -> println(it)},
                {err -> println(err)}
        )
    }

    @Test
    fun getLeisureByNameMustGetResponse(){
        val leisureName = "가로수길 그래피티 낙서해봐 2019"
        val leisureResponse : Leisure? = leisureDataManager.getLeisureByName(leisureName)
        println(leisureResponse)
        assertNotNull(leisureResponse)
        assertEquals(leisureName, leisureResponse?.title)
    }
}