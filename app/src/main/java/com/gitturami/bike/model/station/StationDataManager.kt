package com.gitturami.bike.model.station

import android.content.Context
import com.gitturami.bike.di.dagger.retrofit.DaggerRetrofitComponent
import com.gitturami.bike.di.dagger.retrofit.RetrofitComponent
import com.gitturami.bike.di.dagger.retrofit.RetrofitModule
import com.gitturami.bike.di.retrofit.RetrofitConfig
import javax.inject.Inject

class StationDataManager(context: Context) {
    @Inject
    lateinit var retrofitConfig: RetrofitConfig

    init {
        injectRetrofit(context)
    }

    private fun injectRetrofit(context: Context) {
        val component = DaggerRetrofitComponent.builder()
                .retrofitModule(RetrofitModule(context))
                .build()
        component.inject(this)
    }

    fun getRetrofitConfing() : RetrofitConfig = retrofitConfig
}