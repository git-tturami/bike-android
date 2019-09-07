package com.gitturami.bike.di.model.station

import com.gitturami.bike.model.station.StationDataManager
import com.gitturami.bike.view.main.presenter.MainPresenter
import dagger.Component

@Component(modules = [StationDataManagerModule::class])
interface StationDataManagerComponent {
    fun inject(mainPresenter: MainPresenter)

    @Component.Builder
    interface Builder {
        fun build(): StationDataManagerComponent

        fun stationDataManagerModule(stationDataManagerModule: StationDataManagerModule): Builder
    }
}