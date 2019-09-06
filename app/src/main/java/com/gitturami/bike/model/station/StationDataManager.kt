package com.gitturami.bike.model.station

import com.gitturami.bike.di.dagger.retrofit.DaggerRetrofitComponent
import com.gitturami.bike.di.retrofit.RetrofitConfig
import javax.inject.Inject

class StationDataManager {
    @Inject
    lateinit var retrofitConfig: RetrofitConfig

    fun StationDataManager() {
        injectRetrofit()
    }

    private fun injectRetrofit() {
        DaggerRetrofitComponent
    }
}