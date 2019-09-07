package com.gitturami.bike.di.dagger.retrofit

import android.content.Context
import com.gitturami.bike.model.DataManager
import com.gitturami.bike.model.station.StationDataManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {
    //fun inject(stationDataManager: StationDataManager)
    fun inject(dataManager: DataManager)

    @Component.Builder
    interface Builder {
        fun build() : RetrofitComponent

        fun retrofitModule(retrofitModule: RetrofitModule) : Builder
    }
}