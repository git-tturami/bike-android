package com.gitturami.bike.di.retrofit

import android.content.Context
import com.gitturami.bike.R
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetrofitConfigTest {
    lateinit var retrofitConfig: RetrofitConfig
    @Mock lateinit var context: Context

    @Before fun setUp() {
        Mockito.`when`(context.getString(R.string.serverAddr)).thenReturn("http://127.0.0.1")
        retrofitConfig = RetrofitConfig(context)
    }

    @Test fun testGetRetrofitConfigMustNotNull() {
        val config = retrofitConfig.getRetrofit()
        assertNotNull(config)
        System.out.println(config)
    }
}