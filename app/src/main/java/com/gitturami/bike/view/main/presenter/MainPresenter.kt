package com.gitturami.bike.view.main.presenter

import android.content.Context
import com.gitturami.bike.di.model.DaggerDataManagerComponent
import com.gitturami.bike.di.model.DataManagerModule
import com.gitturami.bike.model.station.StationDataManager
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.cafe.CafeDataManager
import com.gitturami.bike.model.restaurant.RestaurantDataManager
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.state.State

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Inject
import io.reactivex.schedulers.Schedulers

class MainPresenter(context: Context) : MainContact.Presenter {
    companion object {
        val TAG = "MainPresenter"
    }

    lateinit var view: MainContact.View
    val disposal = CompositeDisposable()
    private var state: State = State.PREPARE
    private var startStation: Station? = null
    private var finishStation: Station? = null

    @Inject
    lateinit var stationDataManager: StationDataManager

    @Inject
    lateinit var restaurantDataManager: RestaurantDataManager

    @Inject
    lateinit var cafeDataManager: CafeDataManager

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

    override fun getState(): State = state

    override fun setState(state: State) {
        if (this.state != state) {
            Logger.i(TAG, "setState: $state")
            this.state = state
        }
    }

    override fun registerObserver() {
        Logger.i(TAG, "registerObserver()")
        disposal.add(stationDataManager.allStationList
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
                .flatMap{list -> Observable.fromIterable(list)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Logger.i(TAG, "onNext()") },
                        { e ->
                            Logger.e(TAG, "onError(): $e")
                            view.showToast("식당 정보를 받아오는 도중에 문제가 발생했습니다.")
                        },
                        { Logger.i(TAG, "onComplete()") }
                )
        )

        disposal.add(cafeDataManager.allCafe
                .flatMap{list -> Observable.fromIterable(list)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Logger.i(TAG, "onNext()") },
                        { e ->
                            Logger.e(TAG, "onError(): $e")
                            view.showToast("카페 정보를 받아오는 도중에 문제가 발생했습니다.")
                        },
                        { Logger.i(TAG, "onComplete()") }
                )
        )
    }

    override fun setSearchView(text: String) {
        when (state) {
            State.PREPARE -> view.setStartSearchView(text)
            State.SET_START -> view.setFinishSearchView(text)
        }
    }

    override fun setLocation(station: Station?) {
        when (state) {
            State.SET_START -> setStartStation(station)
            State.SET_FINISH -> setFinishStation(station)
        }
    }

    private fun setStartStation(station: Station?) {
        startStation = station
    }

    private fun setFinishStation(station: Station?) {
        finishStation = station
        if (station != null) view.findPath(startStation!!, finishStation!!)
        else view.clearPath()
    }

    override fun destroy() {
        disposal.dispose()
    }
}

