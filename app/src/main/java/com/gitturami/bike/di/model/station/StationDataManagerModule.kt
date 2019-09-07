package com.gitturami.bike.di.model.station

import android.content.Context
import com.gitturami.bike.model.station.StationDataManager
import dagger.Module
import dagger.Provides

@Module
class StationDataManagerModule(val context: Context) {
    @Provides
    fun provideStationDataManager() : StationDataManager = StationDataManager(context)
}