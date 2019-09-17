package com.gitturami.bike.model.restaurant

import com.gitturami.bike.model.restaurant.pojo.Restaurant
import com.gitturami.bike.model.restaurant.pojo.RestaurantResponse
import android.content.Context
import com.gitturami.bike.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestaurantDataManagerTest {

    private lateinit var restaurantDataManager: RestaurantDataManager
    @Mock private lateinit var context: Context

    @Before
    fun setUp() {
        Mockito.`when`(context.getString(R.string.serverAddr)).thenReturn("http://ec2-15-164-94-0.ap-northeast-2.compute.amazonaws.com:8080")
        restaurantDataManager = RestaurantDataManager(context)
    }

    @Test fun retrofitConfigMustBeCreated() {
        println(restaurantDataManager.retrofitConfig)
        Assert.assertNotNull(restaurantDataManager.retrofitConfig)
    }

    @Test fun getAllRestaurantMustGetResponse() {
        val restaurantResponse = restaurantDataManager.allRestaurant
        restaurantResponse.subscribe(
                {it -> println(it)},
                {err -> println(err)}
        )
    }
}