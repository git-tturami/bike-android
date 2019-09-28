package com.gitturami.bike.di.dagger.retrofit

import com.gitturami.bike.model.DataManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {
    fun inject(dataManager: DataManager)

    @Component.Builder
    interface Builder {
        fun build(): RetrofitComponent

        fun retrofitModule(retrofitModule: RetrofitModule): Builder
    }
}