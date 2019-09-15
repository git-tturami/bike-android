package com.gitturami.bike.view.main.presenter

import android.content.Context
import com.gitturami.bike.di.model.DaggerDataManagerComponent
import com.gitturami.bike.di.model.DataManagerModule
import com.gitturami.bike.model.station.StationDataManager
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.restaurant.RestaurantDataManager

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Inject

import io.reactivex.schedulers.Schedulers

class MainPresenter(context: Context) : MainContact.Presenter {
    companion object {
        val TAG = "MainPresenter"
    }

    private lateinit var view: MainContact.View
    private val disposal = CompositeDisposable()

    @Inject
    lateinit var stationDataManager: StationDataManager
    @Inject
    lateinit var restaurantDataManager: RestaurantDataManager

    init {
        injectDataManager(context)
    }

    fun injectDataManager(context: Context) {
        DaggerDataManagerComponent.builder()
                .dataManagerModule(DataManagerModule(context))
                .build()
                .inject(this)
    }

    override fun takeView(view: MainContact.View) {
        this.view = view
    }

    override fun registerObserver() {
        Logger.i(TAG, "registerObserver()")
        disposal.add(stationDataManager.getAllStationList
                .flatMap{list -> Observable.fromIterable(list)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.setMarker(it.stationLatitude.toDouble(), it.stationLongitude.toDouble(), it)
                        },
                        { e ->
                            Logger.e(TAG, "onError() : $e")
                            view.showToast("따릉이 정거장 정보를 받아오는 도중에 문제가 발생했습니다.")
                        },
                        { Logger.i(TAG, "onComplete()") }
                )
        )

        disposal.add(restaurantDataManager.allRestaurant
                .flatMap{response -> Observable.fromIterable(response.crtfcUpsoInfo.row)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Logger.i(TAG, "onNext(): $it") },
                        { e ->
                            Logger.e(TAG, "onError(): $e")
                            view.showToast("카페 정보를 받아오는 도중에 문제가 발생했습니다.")
                        },
                        { Logger.i(TAG, "onComplete()") }
                )
        )
    }

    override fun destroy() {
        disposal.dispose()
    }
}

