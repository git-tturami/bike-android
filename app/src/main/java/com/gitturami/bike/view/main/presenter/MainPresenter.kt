package com.gitturami.bike.view.main.presenter

import android.content.Context
import com.gitturami.bike.di.model.DaggerDataManagerComponent
import com.gitturami.bike.di.model.DataManagerModule
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.cache.CacheList
import com.gitturami.bike.model.cache.CacheManager
import com.gitturami.bike.model.cafe.CafeDataManager
import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.model.leisure.LeisureDataManager
import com.gitturami.bike.model.path.PathManager
import com.gitturami.bike.model.restaurant.RestaurantDataManager
import com.gitturami.bike.model.station.StationDataManager
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.model.station.pojo.SummaryStation
import com.gitturami.bike.view.main.MainContact
import com.gitturami.bike.view.main.map.ItemType
import com.gitturami.bike.view.main.presenter.handler.*
import com.gitturami.bike.view.main.state.State
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter(context: Context) : MainContact.Presenter {
    companion object {
        val TAG = "MainPresenter"
    }

    lateinit var view: MainContact.View
    val disposal = CompositeDisposable()
    private var prevState: State = State.PREPARE
    private var state: State = State.PREPARE
    private var startStation: Station? = null
    private var finishStation: Station? = null
    private var wayPoint: DefaultItem? = null
    private var distance: Int = 0
    private val stateHandlers: Map<State, StateHandler> = hashMapOf(
            State.PREPARE to PrepareHandler(),
            State.SET_START to SetStartHandler(),
            State.SET_FINISH to SetFinishHandler(),
            State.SELECT_CATEGORY to CategoryHandler(),
            State.SELECT_WAYPOINT to WayPointHandler(),
            State.POST_SELECT_WAYPOINT to PostSelectHandler(),
            State.CLICKED_WAYPOINT to ClickedWayPointHandler(),
            State.SHOW_SCREENSHOT to ScreenshotHandler()
    )
    private var cacheManager: CacheManager

    private val cacheMap: Map<ItemType, CacheList> = hashMapOf(
            ItemType.STATION to CacheList(1000000L),
            ItemType.CAFE to CacheList(1000000L),
            ItemType.RESTAURANT to CacheList(1000000L),
            ItemType.TERRAIN to CacheList(1000000L),
            ItemType.LEISURE to CacheList(1000000L)
    )

    @Inject
    lateinit var stationDataManager: StationDataManager

    @Inject
    lateinit var restaurantDataManager: RestaurantDataManager

    @Inject
    lateinit var cafeDataManager: CafeDataManager

    @Inject
    lateinit var leisureDataManager: LeisureDataManager

    @Inject
    lateinit var pathManager: PathManager

    init {
        injectDataManager(context)
        cacheManager = CacheManager()
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

    override fun restoreState() {
        setState(prevState)
    }

    override fun setState(state: State) {
        if (this.state != state) {
            Logger.i(TAG, "setState: $state")
            this.prevState = this.state
            this.state = state
            stateHandlers[state]?.onStateChanged(view)
        }
    }

    override fun loadDetailInfoOfStation(id: String) {
        Logger.i(TAG, "loadDetailInfoOfStation")
        // setState(State.SHOW_START)
        view.startLoading()
        disposal.add(
                stationDataManager.getStationById(id)!!
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { station ->
                                    view.setSelectBottomSheet(station)
                                    view.endLoading()
                                },
                                { e ->
                                    Logger.e(TAG, "onError : $e")
                                    view.showToast("따릉이 정거장 로드 중 오류가 발생했습니다.")
                                    view.endLoading()
                                })
        )
    }

    override fun loadDetailInfoOfCafe(name: String) {
        view.startLoading()
        disposal.add(
                cafeDataManager.getCafeByName(name)!!
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { cafe ->
                            view.setItemBottomSheet(cafe)
                            view.endLoading()
                        }
        )
    }

    override fun loadDetailInfoOfLeisure(title: String) {
        view.startLoading()
        disposal.add(
                leisureDataManager.getLeisureByName(title)!!
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { leisure ->
                            view.setItemBottomSheet(leisure)
                            view.endLoading()
                        }
        )
    }

    override fun loadDetailInfoOfRestaurant(name: String) {
        // TODO: implement to search restaurant by name
    }

    // TODO : Below setters can be combined.
    override fun setStationMarkers() {
        Logger.i(TAG, "#### Request station information ####")
        view.startLoading()
        val currentTime = System.currentTimeMillis()
        val cacheList = cacheMap[ItemType.STATION]
        val needCache = cacheList!!.needCache(currentTime)
        var observable =
                when (needCache) {
                    true -> {
                        Logger.i(TAG, "needCache true")
                        cacheList.requestedTime = currentTime
                        cacheList.itemList.clear()
                        stationDataManager.getAllSummaryStationList()
                                .flatMap { list -> Observable.fromIterable(list) }
                    }
                    false -> {
                        Logger.i(TAG, "needCache false")
                        Observable.fromIterable(cacheList.itemList)
                    }
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    val shared = (it as SummaryStation).shared
                                    val type = when {
                                        shared > 70 -> ItemType.STATION_EMPTY
                                        shared in 20..70 -> ItemType.STATION_SUITE
                                        else -> ItemType.STATION_FULL
                                    }
                                    view.setMarker(type, it) {
                                        Logger.i(TAG, "tap on station marker")
                                        loadDetailInfoOfStation(it.getID())
                                        when (state) {
                                            State.PREPARE -> {
                                                setState(State.SHOW_START)
                                            }
                                            State.SET_START -> {
                                                setState(State.SHOW_FINISH)
                                            }
                                        }
                                    }
                                    view.setMarkerColorByShared(it.getID(), it.shared)
                                    if (needCache) {
                                        cacheList.itemList.add(it)
                                    }
                                },
                                { e ->
                                    Logger.e(TAG, "onError() : $e")
                                    view.showToast("따릉이 정거장 정보를 받아오는 도중에 문제가 발생했습니다.")
                                    cacheList.clear()
                                    view.endLoading()
                                },
                                {
                                    Logger.i(TAG, "onComplete()")
                                    view.onCompleteMarking()
                                    view.endLoading()
                                }
                        )
        disposal.add(observable)
    }

    override fun setShoppingMarkers() {
        Logger.i(TAG, "#### Request shopping information ####")
        view.startLoading()
        disposal.add(leisureDataManager.summariesShopping
                .flatMap { list -> Observable.fromIterable(list) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.setMarker(ItemType.SHOPPING, it) {
                                requestDetailItem(ItemType.SHOPPING, it.getID())
                            }
                            view.addWayPointItem(it)
                        },
                        {
                            e ->
                            Logger.e(TAG, "onError(): $e")
                            view.showToast("쇼핑 정보를 받아오는 도중에 문제가 발생했습니다.")
                        },
                        {
                            Logger.i(TAG, "onComplete() : set recycler view")
                            view.onCompleteMarking()
                            view.endLoading()
                        }
                )
        )
    }

    override fun setCafeMarkers() {
        // TODO : CAFE
        Logger.i(TAG, "#### Request cafe information ####")
        view.startLoading()
        disposal.add(cafeDataManager.summariesCafe
                .flatMap { list -> Observable.fromIterable(list) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.setMarker(ItemType.CAFE, it) {}
                            view.addWayPointItem(it)
                        },
                        { e ->
                            Logger.e(TAG, "onError(): $e")
                            view.showToast("카페 정보를 받아오는 도중에 문제가 발생했습니다.")
                        },
                        {
                            Logger.i(TAG, "onComplete() : set recycler view")
                            view.onCompleteMarking()
                            view.endLoading()
                        }
                )
        )
    }

    override fun setLeisureMarkers() {
        Logger.i(TAG, "#### Request leisure information ####")

        view.startLoading()
        val currentTime = System.currentTimeMillis()
        val cacheList = cacheMap.getValue(ItemType.LEISURE)
        val needCache = cacheList.needCache(currentTime)
        var observable =
                when (needCache) {
                    true -> {
                        cacheList.requestedTime = currentTime
                        cacheList.itemList.clear()
                        leisureDataManager.summariesCourses
                                .flatMap { list -> Observable.fromIterable(list) }
                    }
                    false -> {
                        Observable.fromIterable(cacheList.itemList)
                    }
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    view.setMarker(ItemType.LEISURE, it) {
                                        requestDetailItem(ItemType.LEISURE, it.getID())
                                    }
                                    view.addWayPointItem(it)
                                    if (needCache) {
                                        cacheList.itemList.add(it)
                                    }
                                },
                                { e ->
                                    Logger.e(TAG, "onError(): $e")
                                    view.showToast("레저 정보를 받아오는 도중에 문제가 발생했습니다.")
                                    cacheList.clear()
                                },
                                {
                                    Logger.i(TAG, "onComplete() : set recycler view")
                                    view.onCompleteMarking()
                                    view.endLoading()
                                }
                        )
        disposal.add(observable)
    }

    override fun setTerrainMarkers() {
        Logger.i(TAG, "#### Request Terrain information ####")
        view.startLoading()
        val currentTime = System.currentTimeMillis()
        val cacheList = cacheMap.getValue(ItemType.TERRAIN)
        val needCache = cacheList.needCache(currentTime)
        val observable = when (needCache) {
            true -> {
                leisureDataManager.summariesTerrain
                        .flatMap { list -> Observable.fromIterable(list) }
            }
            false -> {
                Observable.fromIterable(cacheList.itemList)
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.setMarker(ItemType.TERRAIN, it) {
                                requestDetailItem(ItemType.TERRAIN, it.getID())
                            }
                            view.addWayPointItem(it)
                            if (needCache) {
                                cacheList.itemList.add(it)
                            }
                        },
                        { e ->
                            Logger.e(TAG, "onError(): $e")
                            view.showToast("자연 정보를 받아오는 도중에 문제가 발생했습니다.")
                            cacheList.clear()
                        },
                        {
                            Logger.i(TAG, "onComplete() : set recycler view")
                            view.onCompleteMarking()
                            view.endLoading()
                        }
                )

        disposal.add(observable)
    }

    override fun setRestaurantMarkers() {
        Logger.i(TAG, "#### Request restaurant information ####")
        view.startLoading()
        val currentTime = System.currentTimeMillis()
        val cacheList = cacheMap.getValue(ItemType.RESTAURANT)
        val needCache = cacheList.needCache(currentTime)
        val observable = when (needCache) {
            true -> {
                restaurantDataManager.allLightRestaurant.flatMap { list -> Observable.fromIterable(list) }
            }
            false -> {
                Observable.fromIterable(cacheList.itemList)
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.setMarker(ItemType.RESTAURANT, it) {
                                requestDetailItem(ItemType.RESTAURANT, it.getID())
                            }
                            view.addWayPointItem(it)
                            if (needCache) {
                                cacheList.itemList.add(it)
                            }
                        },
                        { e ->
                            Logger.e(TAG, "onError(): $e")
                            view.showToast("식당 정보를 받아오는 도중에 문제가 발생했습니다.")
                            cacheList.clear()
                        },
                        {
                            Logger.i(TAG, "onComplete() : set recycler view")
                            view.onCompleteMarking()
                            view.endLoading()
                        }
                )
        disposal.add(observable)
    }

    override fun setSearchView(text: String) {
        when (state) {
            State.SHOW_START -> {
                view.setStartSearchView(text)
                setState(State.SET_START)
            }
            State.SHOW_FINISH -> {
                view.setFinishSearchView(text)
                setState(State.SET_FINISH)
            }
        }
    }

    override fun setLocation(station: Station?) {
        when (state) {
            State.SET_START -> setStartStation(station)
            State.SET_FINISH -> setFinishStation(station)
            State.SELECT_CATEGORY -> {
                setStartStation(null)
                setFinishStation(null)
            }
        }
    }

    override fun requestDetailItem(type: ItemType, param: String) {
        // TODO : We need to refactor this method.
        Logger.i(TAG, "requestDetailItem")
        view.startLoading()
        when (type) {
            ItemType.CAFE -> {
                cafeDataManager.getCafeByName(param)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    view.setItem(it)
                                    setState(State.POST_SELECT_WAYPOINT)
                                    view.endLoading()
                                },
                                { e ->
                                    Logger.e(TAG, "onError() : $e")
                                }
                        )
            }
            ItemType.RESTAURANT -> {
                restaurantDataManager.restaurantByName(param)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    Logger.i(TAG, "$it")
                                    view.setItem(it)
                                    setState(State.POST_SELECT_WAYPOINT)
                                    view.endLoading()
                                },
                                { e ->
                                    Logger.e(TAG, "onError() : $e")

                                }
                        )
            }
            ItemType.LEISURE, ItemType.TERRAIN, ItemType.SHOPPING -> {
                leisureDataManager.getLeisureByName(param)!!
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    Logger.i(TAG, "$it")
                                    view.setItem(it)
                                    setState(State.POST_SELECT_WAYPOINT)
                                    view.endLoading()
                                },
                                { e ->
                                    Logger.e(TAG, "onError() : $e")
                                }
                        )
            }
        }
    }

    override fun resetPath() {
        findPath(startStation!!, finishStation!!)
    }

    override fun findPath(start: Station, end: Station) {
        disposal.add(
                pathManager.getPath(start.stationLongitude.toDouble(),
                        start.stationLatitude.toDouble(),
                        end.stationLongitude.toDouble(),
                        end.stationLatitude.toDouble(),
                        start.stationName,
                        end.stationName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    //Logger.i(TAG, "$it")
                                    this.distance = view.markPath(it)
                                    setState(State.SELECT_CATEGORY)
                                },
                                { t -> Logger.e(TAG, "$t") }
                        )
        )
    }

    override fun setWayPointAndFindPath(wayPoint: DefaultItem) {
        Logger.i(TAG, "wayPoint : $wayPoint")
        disposal.add(
                pathManager.getPathIncludeWayPoint(startStation!!.stationLongitude.toDouble(),
                        startStation!!.stationLatitude.toDouble(),
                        finishStation!!.stationLongitude.toDouble(),
                        finishStation!!.stationLatitude.toDouble(),
                        startStation!!.stationName,
                        finishStation!!.stationName,
                        wayPoint.getLongitude() + "," + wayPoint.getLatitude())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    this.distance = view.markPath(it)
                                    this.wayPoint = wayPoint
                                    setState(State.CLICKED_WAYPOINT)
                                    view.setWayPointMarker(wayPoint)
                                },
                                { t -> Logger.e(TAG, "$t") }
                        )
        )
    }

    private fun setStartStation(station: Station?) {
        startStation = station
        if (station != null) view.setStartMarker(station)
    }

    private fun setFinishStation(station: Station?) {
        if (station == null) {
            view.clearPath()
        }
        finishStation = station
        if (station != null) {
            findPath(startStation!!, finishStation!!)
            view.setFinishMarker(station)
        }
    }

    override fun destroy() {
        disposal.dispose()
    }

    override fun getStartStationName(): String? {
        val list = startStation!!.stationName.split(" ")
        var name = ""
        for (i in list.indices) {
            if (i == 0) continue
            name += (list[i] + " ")
        }
        return name
    }

    override fun getEndStationName(): String? {
        val list = finishStation!!.stationName.split(" ")
        var name = ""
        for (i in list.indices) {
            if (i == 0) continue
            name += (list[i] + " ")
        }
        return name
    }

    override fun getWayPointName(): String? {
        return wayPoint!!.getName()
    }

    override fun getDistance(): Int {
        return distance
    }
}

