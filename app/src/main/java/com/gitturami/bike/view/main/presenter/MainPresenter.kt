package com.gitturami.bike.view.main.presenter

import android.content.Context
import com.gitturami.bike.di.model.DaggerDataManagerComponent
import com.gitturami.bike.di.model.DataManagerModule
import com.gitturami.bike.model.station.StationDataManager
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.cafe.CafeDataManager
import com.gitturami.bike.model.restaurant.RestaurantDataManager
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.SummaryStation
import com.gitturami.bike.view.main.MainContact
import com.gitturami.bike.view.main.presenter.handler.*
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
    private val stateHandlers: Map<State, StateHandler> = hashMapOf(
            State.PREPARE to PrepareHandler(),
            State.SET_START to SetStartHandler(),
            State.SET_FINISH to SetFinishHandler(),
            State.SELECT_CATEGORY to CategoryHandler(),
            State.SELECT_WAYPOINT to WayPointHandler(),
            State.POST_SELECT_WAYPOINT to PostSelectHandler()
    )

    @Inject
    lateinit var stationDataManager: StationDataManager

    @Inject
    lateinit var restaurantDataManager: RestaurantDataManager

    @Inject
    lateinit var cafeDataManager: CafeDataManager

    init {
        injectDataManager(context)
    }

    private fun injectDataManager(context: Context) {
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
            stateHandlers[state]?.onStateChanged(view)
        }
    }

    override fun loadDetailInfoOfStation(id: String) {
        stationDataManager.getStationById(id)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { station -> view.setSelectBottomSheet(station) }
    }

    // TODO : Below setters can be combined.
    override fun setStationMarkers() {
        Logger.i(TAG, "#### Request station information ####")
        disposal.add(stationDataManager.allSummaryStationList
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
                        {
                            Logger.i(TAG, "onComplete()")
                            view.onCompleteMarking()
                        }
                )
        )
    }

    override fun setCafeMarkers() {
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

    override fun setRestaurantMarkers() {
        Logger.i(TAG, "#### Request restaurant information ####")
        disposal.add(restaurantDataManager.allRestaurant
                .flatMap{list -> Observable.fromIterable(list)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            //Logger.i(TAG, "restaurant : $it")
                            view.setMarker(it.Y_DNTS.toDouble(), it.X_CNTS.toDouble(), it)
                            view.addWayPointItem(it)
                        },
                        { e ->
                            Logger.e(TAG, "onError(): $e")
                            view.showToast("식당 정보를 받아오는 도중에 문제가 발생했습니다.")
                        },
                        {
                            Logger.i(TAG, "onComplete() : set recycler view")
                            view.onCompleteMarking()
                        }
                )
        )
    }

    override fun setSearchView(text: String) {
        when (state) {
            State.PREPARE -> {
                view.setStartSearchView(text)
                setState(State.SET_START)
            }
            State.SET_START -> {
                view.setFinishSearchView(text)
                setState(State.SET_FINISH)
            }
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
        if (station != null) view.changeMarker(station)
    }

    private fun setFinishStation(station: Station?) {
        if (station == null) {
            view.clearPath()
        }
        finishStation = station
        if (station != null) {
            view.findPath(startStation!!, finishStation!!)
            view.changeMarker(station)
        }
    }

    override fun destroy() {
        disposal.dispose()
    }
}

