package com.gitturami.bike.di.retrofit

import android.content.Context
import com.gitturami.bike.R
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig(val context: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.serverAddr))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getRetrofit(): Retrofit {
        return retrofit
    }
}