package com.gitturami.bike.model.cafe

import com.gitturami.bike.model.cafe.pojo.CafeResponse
import com.gitturami.bike.model.cafe.pojo.Cafe
import android.content.Context
import com.gitturami.bike.R
import com.gitturami.bike.model.cafe.CafeDataManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CafeDataMangerTest{

    private lateinit var cafeDataManager: CafeDataManager
    @Mock private lateinit var context: Context

    @Before
    fun setUp(){
        Mockito.`when`(context.getString(R.string.serverAddr)).thenReturn("http://ec2-15-164-94-0.ap-northeast-2.compute.amazonaws.com:8080")
        cafeDataManager = CafeDataManager(context)
    }

    @Test
    fun retrofitConfigMustBeCreated(){
        println(cafeDataManager.retrofitConfig)
        Assert.assertNotNull(cafeDataManager.retrofitConfig)
    }

    @Test
    fun getAllCafeMustGetResponse() {
        val cafeResponse = cafeDataManager.allCafe
        cafeResponse.subscribe(
                {it -> println(it)},
                {err -> println(err)}
        )
    }
}