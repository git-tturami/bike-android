package com.gitturami.bike.di.model

import com.gitturami.bike.view.main.presenter.MainPresenter
import dagger.Component

@Component(modules = [DataManagerModule::class])
interface DataManagerComponent {
    fun inject(mainPresenter: MainPresenter)

    @Component.Builder
    interface Builder {
        fun build(): DataManagerComponent

        fun dataManagerModule(module: DataManagerModule): Builder
    }
}