package com.gitturami.bike.model.path

import android.content.Context
import com.gitturami.bike.R
import com.gitturami.bike.model.leisure.LeisureDataManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PathManagerTest {
    private lateinit var manager: PathManager
    @Mock
    private lateinit var context: Context

    @Before
    fun setUp(){
        Mockito.`when`(context.getString(R.string.serverAddr)).thenReturn("http://ec2-15-164-94-0.ap-northeast-2.compute.amazonaws.com:8080")
        manager = PathManager(context)
    }

    @Test
    fun testFindPath() {
        val item = manager.getPath(126.98040009, 37.56134033,  126.99143219, 37.56439972,
                "%EC%B6%9C%EB%B0%9C", "%EB%B3%B8%EC%82%AC")

        item.subscribe(
                {
                    println(it)
                },
                {t -> println(t)}
        )
    }
}