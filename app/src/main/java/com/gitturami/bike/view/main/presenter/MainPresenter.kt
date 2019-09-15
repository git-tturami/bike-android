package com.gitturami.bike.view.main.presenter

import android.content.Context
import com.gitturami.bike.di.model.station.DaggerStationDataManagerComponent
import com.gitturami.bike.di.model.station.StationDataManagerModule
import com.gitturami.bike.model.station.StationDataManager
import com.gitturami.bike.adapter.contact.RecommendAdapterContact
import com.gitturami.bike.logger.Logger
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

    @Inject
    lateinit var stationDataManager: StationDataManager

    init {
        injectDataManager(context)
    }

    fun injectDataManager(context: Context) {
        DaggerStationDataManagerComponent.builder()
                .stationDataManagerModule(StationDataManagerModule(context))
                .build()
                .inject(this)
    }

    override fun takeView(view: MainContact.View) {
        this.view = view
    }

    override fun updateMarker() {
        Logger.i(TAG, "updateMarker()")
        val disposal = CompositeDisposable()
        disposal.add(stationDataManager.getAllStationList
                .flatMap{list -> Observable.fromIterable(list)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.setMarker(it.stationLatitude.toDouble(), it.stationLongitude.toDouble(), it)
                        },
                        {Logger.e(TAG, "onError() : $it")},
                        {Logger.i(TAG, "onComplete()")}
                ))
    }
}

