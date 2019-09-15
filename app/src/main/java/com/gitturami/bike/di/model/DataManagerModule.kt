package com.gitturami.bike.di.model

import android.content.Context
import com.gitturami.bike.model.restaurant.RestaurantDataManager
import com.gitturami.bike.model.station.StationDataManager
import dagger.Module
import dagger.Provides

@Module
class DataManagerModule(val context: Context) {
    @Provides
    fun provideStationDataManager() = StationDataManager(context)

    @Provides
    fun provideRestaurantDataManager() = RestaurantDataManager(context)
}