package com.gitturami.bike.di.dagger.retrofit

import android.content.Context
import com.gitturami.bike.di.retrofit.RetrofitConfig
import dagger.Module
import dagger.Provides

@Module
class RetrofitModule(val context: Context) {

    @Provides
    fun provideRetrofit() : RetrofitConfig {
        return RetrofitConfig(context)
    }
}